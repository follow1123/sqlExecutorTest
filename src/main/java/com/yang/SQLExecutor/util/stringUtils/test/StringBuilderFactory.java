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
    private static List<StringBuilder> builders = new ArrayList<>();
    //临时引用
    private static StringBuilder temp;

    /**
     * 判断当前sb是否可用，就是判断当前sb的长度是否为0
     * @param sb
     * @return
     */
    private static boolean isUnused(StringBuilder sb) {
        return sb.length() == 0;
    }

    /**
     * get
     * @return
     */
    public static List<StringBuilder> getBuilders() {
        return builders;
    }

    /**
     * 获取一个新的StringBuilder对象并存入数组
     * @return
     */
    public static StringBuilder getNewBuilder() {
        builders.add(temp = new StringBuilder());
        return temp;
    }

    /**
     * 回收StringBuilder对象，就是将StringBuilder对象里面的字符串清空
     * @param sb
     */
    public static void recycleBuilder(StringBuilder sb){
        sb.delete(0, sb.length());
    }

    /**
     * 根据情况获取一个StringBuilder对象
     * @return
     */
    public static StringBuilder create() {
        if (builders.size() == 0) {
            synchronized (StringBuilderFactory.class) {
                if (builders.size() == 0) {
                    System.out.println(Thread.currentThread().getName() + " get a new builder !");
                    return getNewBuilder();
                }
            }
        }
        synchronized (StringBuilderFactory.class) {
            List<StringBuilder> collect = builders.stream().filter(StringBuilderFactory::isUnused).collect(Collectors.toList());
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
     * 使用StringBuilder的通用操作
     * @param event
     */
    public static void useBuilder(Consumer<StringBuilder> event){
        StringBuilder sb = create();
        event.accept(sb);
        recycleBuilder(sb);
    }

}
