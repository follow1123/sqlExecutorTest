package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory.newS;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @auther YF
 * @create 2020-11-24-18:12
 */
public class TestThread02 {

    static volatile boolean autoFlag = true;
    static int minSize = 10;
    static List<Integer> ints = new LinkedList<>();

    static Thread t = new Thread(() -> {
        while (true) {
            if (autoFlag) {
                sleep(1000);
                autoFlag = false;
                if (!autoFlag) {
                    System.out.println("关门");
                    clean();
                }
            }
        }
    });

    private static void clean(){
        if (ints.size() > minSize){
            for (int i = 0; i < ints.size() - minSize; i++) {
                sleep(1000);
                if (autoFlag) break;
                synchronized (TestThread02.class){
                    ints.remove(i);
                }
            }
        }
        System.out.println("清理后 ====> " + ints.size());
    }


    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startListener() {
        if (t.getState() == Thread.State.NEW) {
            synchronized (TestThread02.class) {
                if (t.getState() == Thread.State.NEW) {
                    System.out.println("开始监听");
                    t.setDaemon(true);
                    t.start();
                }
            }
        }
    }

    public static void main(String[] args) {
        test01();
    }

    private static void openDoor()  {
        if (!autoFlag) {
            autoFlag = true;
            System.out.println(Thread.currentThread().getName() + "开门进入");
        } else {
            System.out.println(Thread.currentThread().getName() + "直接进入");
        }
    }


    private static void addInts() {
        for (int i = 0; i < 5; i++) {
            synchronized (TestThread02.class) {
                ints.add(i);
            }
        }
        System.out.println(ints.size());
    }

    private static void operation() {
        startListener();
        openDoor();
        addInts();
    }


    private static void test01() {
        Queue<Integer> integers = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            new Thread(TestThread02::operation).start();
            sleep(500);
        }
        sleep(3000);
        for (int i = 0; i < 10; i++) {
            new Thread(TestThread02::operation).start();
//            sleep(200);
        }
    }

}
