package com.yang.SQLExecutor.util.stringUtils.test.newStructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * @auther YF
 * @create 2020-11-25-21:00
 */
public class DynamicObjectPool<T> {

    //StringBuilder数组
    private LinkedList<Reusable> objs;
    //临时引用
    private Reusable temp;
    //空闲对象集合
    private LinkedList<Reusable> idleObjs;
    //守护线程的标记，标记是否可以清理
    private volatile boolean autoFlag = true;
    //对象集合默认的最小size
    private int minSize;
    //守护线程实例
    private Thread objGC;

    private Constructor<T> prototypeConstructor;

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
    public Reusable getNewBuilder(){
        try {
            objs.add(temp = (Reusable) prototypeConstructor.newInstance());
        }catch (ClassCastException e){
            e.printStackTrace();
            throw new RuntimeException("please implement a interface 'Reusable'");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
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
        ((Reusable)t).recycle();
    }
}
