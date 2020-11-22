package com.yang.SQLExecutor.util.stringUtils.test.newStructure;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    private volatile static String curTName;


    private volatile static long curMS;

    private static int minSize = 50;

    public synchronized static void setCurTName() {
        System.out.println(
                (curTName = Thread.currentThread().getName()) +
                        " join on " +
                        (curMS = System.currentTimeMillis()) + " ms");
    }

    public static List<Reusable> getObjs() {
        return objs;
    }

    private static Thread objGC = new Thread(() -> {
        long preMS = 0;
        while (true) {
            if (curMS - preMS >= 1000) {
                System.out.println("------------------------------------------ delay ------------------------------------------");
                System.out.println("final  ==== " + objs.size());
            }
            preMS = curMS;
        }
    });

    static {
        objGC.setDaemon(true);
        objGC.start();
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

    public static Reusable listenerAndGet() {
        setCurTName();
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
