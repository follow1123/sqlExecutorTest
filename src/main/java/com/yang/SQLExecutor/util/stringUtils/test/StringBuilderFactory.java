package com.yang.SQLExecutor.util.stringUtils.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @auther YF
 * @create 2020-11-19-20:42
 */
public class StringBuilderFactory {
    //StringBuilder数组
    private static List<StringBuilderAdapter> builders = new ArrayList<>();
    //临时引用
    private static StringBuilderAdapter temp;

    /**
     * @param sb
     * @return
     */
    private static boolean isUnused(StringBuilderAdapter sb) {
        return !sb.isUsing();
    }


    /**
     * 获取一个新的StringBuilder对象并存入数组
     *
     * @return
     */
    public static StringBuilderAdapter getNewBuilder() {
        builders.add(temp = new StringBuilderAdapter(new StringBuilder()));
        return temp;
    }

    public static List<StringBuilderAdapter> getBuilders() {
        return builders;
    }

    //    /**
//     * 回收StringBuilder对象，就是将StringBuilder对象里面的字符串清空
//     *
//     * @param sb
//     */
//    public static void recycleBuilder(StringBuilder sb) {
//        sb.delete(0, sb.length());
//    }

    /**
     * 根据情况获取一个StringBuilder对象
     *
     * @return
     */
    public static StringBuilderAdapter create() {
        if (builders.size() == 0) {
            synchronized (StringBuilderFactory.class) {
                if (builders.size() == 0) {
                    System.out.println(Thread.currentThread().getName() + " get a new builder !");
                    return getNewBuilder();
                }
            }
        }
        synchronized (StringBuilderFactory.class) {
            List<StringBuilderAdapter> collect = builders.stream().filter(StringBuilderFactory::isUnused).collect(Collectors.toList());
            if (collect.size() > 0) {
                System.out.println(Thread.currentThread().getName() + " get a builder from container !");
                return collect.get(0);
            } else {
                System.out.println(Thread.currentThread().getName() + " get a new builder !");
                return getNewBuilder();
            }
        }
    }

    /**
     * 简化版
     * @return
     */
    public synchronized static StringBuilderAdapter create1() {
        if (builders.size() > 0) {
            List<StringBuilderAdapter> collect = builders.stream().filter(StringBuilderFactory::isUnused).collect(Collectors.toList());
            if (collect.size() > 0) {
//                System.out.println(Thread.currentThread().getName() + " get a builder from container !");
                return collect.get(0).setUsing(true);
            }
        }
//        System.out.println(Thread.currentThread().getName() + " get a new builder !");
        return getNewBuilder();
    }
//空闲对象的数量
    private volatile static int availableCount;
//空闲对象集合
    private static List<StringBuilderAdapter> availableBuilder;
//获取空闲对象的数量和集合
    public synchronized static void getAvailableBuilder() {
        List<StringBuilderAdapter> collect = builders.stream().filter(StringBuilderFactory::isUnused).collect(Collectors.toList());
        if (collect.size() == 0) {
            collect.add(getNewBuilder());
        }
        availableCount = collect.size();
        availableBuilder = collect;
    }
//递归根据index获取空闲数组内的对象
    private static StringBuilderAdapter getAvailable() {
        if (availableCount > 0) {
            synchronized (StringBuilderFactory.class) {
                if (availableCount > 0) {
                    availableCount--;
                    return availableBuilder.get(availableCount).setUsing(true);
                }
            }
        }
        getAvailableBuilder();
        return getAvailable();
    }

    /**
     * 分配空闲对象集合的方式
     * @return
     */
    public static StringBuilderAdapter create2() {
        if (builders.size() == 0) {
            synchronized (StringBuilderFactory.class) {
                if (builders.size() == 0) {
                    return getNewBuilder();
                }
            }
        }
        return getAvailable();
    }

    /**
     * 使用StringBuilder的通用操作
     *
     * @param event
     */
    public static void useBuilder(Consumer<StringBuilderAdapter> event) {
        StringBuilderAdapter sb = create2();
        event.accept(sb);
        sb.recycle();
//        recycleBuilder(sb);
    }

}
