package com.yang.SQLExecutor.util.stringUtils.test.newStructure;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @auther YF
 * @create 2020-11-25-21:00
 */
public class DynamicObjectPool<T extends Reusable> {

    //StringBuilder数组
    private LinkedList<T> objs;
    //临时引用
    private T temp;
    //空闲对象集合
    private LinkedList<T> idleObjs;
    //守护线程的标记，标记是否可以清理
    private volatile boolean autoFlag = true;
    //对象集合默认的最小size
    private int minSize;
    //守护线程实例
    private Thread objGC;
    //原型对象构造器
    private Constructor<T> prototypeConstructor;
    //原型对象实例
    private T prototype;

    /**
     * 初始化参数
     */
    private void init() {
        objs = new LinkedList<>();
        idleObjs = new LinkedList<>();
        autoFlag = true;
        objGC = new Thread(this::listenerEvent);
    }

    DynamicObjectPool(Class<T> prototype, int minSize) {
        init();
        this.minSize = minSize;
        try {
            this.prototypeConstructor = prototype.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("no public constructor!");
        }
    }

    public DynamicObjectPool(T prototype, int minSize) {
        init();
        this.minSize = minSize;
        this.prototype = prototype;
    }

    /**
     * 根据传参类型以不同的方式获取对象实例
     *
     * @return
     */
    private T getInstanceByPrototype() {
        if (prototype != null) {
            return DeepCloneUtil.clone(prototype);
        } else if (prototypeConstructor != null) {
            try {
                return prototypeConstructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 自动清理操作
     */
    private void listenerEvent() {
        while (true) {
            if (autoFlag) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    autoFlag = false;
                    if (!autoFlag) {
                        System.out.println("DynamicObjectPool.sizeListener::开始清理");
                        clean();
                    }
                }
            }
        }
    }

    /**
     * 根据情况清理对象集合里面的对象
     */
    private void clean() {
        System.out.println("DynamicObjectPool.clean::清理前 ====> " + objs.size());
        if (objs.size() > minSize) {
            for (int excessSize = objs.size() - minSize; excessSize > 0; excessSize--) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("exception");
                    break;
                }
                if (autoFlag) {
                    System.out.println("打断");
                    break;
                }
                synchronized (this) {
                    objs.removeFirst();
                }
            }
        }
        System.out.println("DynamicObjectPool.clean::清理后 ====> " + objs.size());
    }

    /**
     * 从对象集合内筛选闲置的对象存入闲置对象集合内
     */
    private void getIdleObjs() {
        objs.stream().filter(Reusable::isIdle).forEach(idleObjs::add);
        if (idleObjs.size() == 0) idleObjs.add(getNewBuilder());
    }

    /**
     * 标记线程的进入
     */
    private void mark() {
        if (!autoFlag) autoFlag = true;
    }

    /**
     * 开启守护线程
     */
    private void startListener() {
        if (objGC.getState() == Thread.State.NEW) {
            synchronized (this) {
                if (objGC.getState() == Thread.State.NEW) {
                    System.out.println("DynamicObjectPool.startListener::开始监听");
                    objGC.setDaemon(true);
                    objGC.start();
                }
            }
        }
    }

    /**
     * 获取当前对象集合的大小
     *
     * @return
     */
    public int getSize() {
        return objs.size();
    }

    /**
     * 获取一个新的StringBuilder对象并存入数组
     *
     * @return
     */
    public T getNewBuilder() {
        objs.add(temp = getInstanceByPrototype());
        return temp;
    }

    /**
     * 监听线程并获取
     *
     * @return
     */
    public T listenerAndGet() {
        startListener();
        return get();
    }

    /**
     * 获取对象
     *
     * @return
     */
    public T get() {
        mark();
        //双重检测锁判断闲置数组是否为空
        if (idleObjs.size() == 0) {
            synchronized (this) {
                //为空则从对象数组内填充
                if (idleObjs.size() == 0) getIdleObjs();
            }
        }
        //到这里闲置数组的大小必然大于零所以这还是一个双重检测锁
        // 判断闲置数组是否大于零
        synchronized (this) {
            //大于零这移除数组内的第一个元素并设置状态后返回
            if (idleObjs.size() > 0) return (T) idleObjs.removeFirst().setIdle(false);
        }
        //到这里说明闲置数组内的对象又被抢空了，递归将刚才的步骤执行一遍就行了
        return get();
    }

    /**
     * 通用操作
     *
     * @param event
     */
    public void use(Consumer<T> event) {
        T t = listenerAndGet();
        event.accept(t);
        t.recycle();
    }

    /**
     * 深克隆工具类
     * 主要使用序列化和反序列化进行深克隆
     */
    public static class DeepCloneUtil {
        //读取流
        private static ByteArrayOutputStream baos;
        private static ObjectOutputStream oos;
        //读取流的状态
        private static boolean inStatus;
        //写出流的我状态
        private static boolean outStatus;
        //写出流
        private static ByteArrayInputStream bais;
        private static ObjectInputStream ois;

        //初始化写出流
        private static void initOut() throws IOException {
            if (!outStatus) {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                outStatus = true;
            }
        }

        //初始化读取流
        private static void initIn() throws IOException {
            if (!inStatus) {
                bais = new ByteArrayInputStream(baos.toByteArray());
                ois = new ObjectInputStream(bais);
                inStatus = true;
            }
        }

        /**
         * 写出一个对象
         *
         * @param o
         */
        private static void writeObj(Object o) {
            if (o == null) return;
            try {
                initOut();
                oos.writeObject(o);
                oos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 读取一个对象
         *
         * @return
         */
        private static Object readObj() {
            try {
                initIn();
                return ois.readObject();
            } catch (EOFException e) {
//                e.printStackTrace();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 根据类型读取一个对象
         *
         * @param rType
         * @param <R>
         * @return
         */
        private static <R> R readObj(Class<R> rType) {
            return (R) readObj();
        }

        /**
         * 关闭所有的流
         */
        private static void closeStream() {
            close(baos, oos, bais, ois);
            inStatus = outStatus = false;
        }

        /**
         * 读取流内所有的对象
         *
         * @return
         */
        private static List<Object> readObjs() {
            ArrayList<Object> objects = new ArrayList<>();
            for (Object v; (v = readObj()) != null; objects.add(v)) ;
            return objects;
        }

        /**
         * 关闭流操
         *
         * @param cs
         */
        private static void close(Closeable... cs) {
            for (Closeable c : cs) {
                if (c != null) {
                    try {
                        c.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 克隆一个对象的操作
         *
         * @param target
         * @param <O>
         * @return
         */
        public synchronized static <O> O clone(O target) {
            writeObj(target);
            O o = (O) readObj();
            closeStream();
            return o;
        }

        /**
         * 克隆一堆对象的操作
         *
         * @param targets
         * @return
         */
        public synchronized static List<Object> clone(Object... targets) {
            return clone(Arrays.asList(targets));
        }

        /**
         * 重载 克隆一堆对象的操作
         *
         * @param targets
         * @return
         */
        public synchronized static List<Object> clone(List<Object> targets) {
            targets.forEach(DeepCloneUtil::writeObj);
            List<Object> objects = readObjs();
            closeStream();
            return objects;
        }

    }
}
