package com.yang.SQLExecutor.util.stringUtils.test.newStructure;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @auther YF
 * @create 2020-11-21-20:59
 */
public class DynamicObjectPool {
    //StringBuilder数组
    private static List<Reusable> objs = new ArrayList<>();
    //临时引用
    private static Reusable temp;
    //空闲对象集合
    private static LinkedList<Reusable> idleObjs = new LinkedList<>();
    //每个线程进入时的毫秒数
    private volatile static long curNanoSecond;
    //对象集合默认的最小size
    private static int minSize = 50;
    //守护线程实例
    private static Thread objGC = new Thread(DynamicObjectPool::sizeListener);

    //开启一个守护线程
    static {
        objGC.setDaemon(true);
        objGC.start();
    }

    /**
     * 自动清理操作
     */
    private static void sizeListener() {
        long preNS = 0;
        while (true) {
//            System.nanoTime()
            if ((preNS + TimeUnit.SECONDS.toNanos(2)) <= curNanoSecond) {
                preNS = curNanoSecond;
            }
        }
    }

    private static void clean() throws InterruptedException {
        int canCleanSize = objs.size() - minSize;
        if (canCleanSize > 0) {
            for (int i = 0; i < canCleanSize; i++) {
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    /**
     * 标记一下每个线程进来时的毫秒数
     */
    public synchronized static void markNanoSecond() {
        System.out.println(Thread.currentThread().getName() + " join on " +
                (curNanoSecond = System.currentTimeMillis()) + " ns");
    }

    /**
     * 获取当前对象集合的大小
     *
     * @return
     */
    public static int getSize() {
        return objs.size();
    }

    /**
     * 获取一个新的StringBuilder对象并存入数组
     *
     * @return
     */
    public static Reusable getNewBuilder() {
        objs.add(temp = new StringBuilderAdapter());
        return temp;
    }

    /**
     * 从对象集合内筛选闲置的对象存入闲置对象集合内
     */
    private static void getIdleObjs() {
        objs.stream().filter(Reusable::isIdle).forEach(idleObjs::add);
        if (idleObjs.size() == 0) idleObjs.add(getNewBuilder());

    }

    /**
     * 监听线程并获取
     *
     * @return
     */
    public static Reusable listenerAndGet() {
        markNanoSecond();
        return get();
    }

    /**
     * 获取对象
     *
     * @return
     */
    public static Reusable get() {
        //双重检测锁判断闲置数组是否为空
        if (idleObjs.size() == 0) {
            synchronized (DynamicObjectPool.class) {
                //为空则从对象数组内填充
                if (idleObjs.size() == 0) getIdleObjs();
            }
        }
        //到这里闲置数组的大小必然大于零所以这还是一个双重检测锁
        // 判断闲置数组是否大于零
        synchronized (DynamicObjectPool.class) {
            //大于零这移除数组内的第一个元素并设置状态后返回
            if (idleObjs.size() > 0) return idleObjs.removeFirst().setIdle(false);
        }
        //到这里说明闲置数组内的对象又被抢空了，递归将刚才的步骤执行一遍就行了
        return get();
    }

    /**
     * 通用操作
     *
     * @param event
     * @param <T>
     */
    public static <T> void use(Consumer<T> event) {
        Reusable t = DynamicObjectPool.listenerAndGet();
        event.accept((T) t);
        t.recycle();
    }
}
