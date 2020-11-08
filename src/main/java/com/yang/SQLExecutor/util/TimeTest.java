package com.yang.SQLExecutor.util;

/**
 * @auther YF
 * @create 2020-11-07-15:36
 */
public class TimeTest {

    private static long start;

    private static long end;

    public static long testTime(Runnable r){
        start = System.currentTimeMillis();
        r.run();
        end = System.currentTimeMillis();
        return clear();
    }
    private static long clear(){
        long time = end - start;
        System.out.println(time);
        start = 0;
        end = 0;
        return time;
    }


}
