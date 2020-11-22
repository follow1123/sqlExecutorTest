package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory.newS;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther YF
 * @create 2020-11-22-13:54
 */
public class TestThread {

    private volatile static String name;


    public static void main(String[] args) {
        test01();
        test02();
    }

    private static void sl(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test01() {
        Thread thread = new Thread(() -> {
            String preName = "--NULL--";
            while (true) {
                if (name != null  && !preName.equals(name)){
                    System.out.println((preName = name));
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Test
    public void test04() {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                for (int i1 = 0; i1 < 10; i1++) {
                    atomicInteger.incrementAndGet();
                }
            }));

        }
        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(atomicInteger.intValue());
    }

    public static void test02() {

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                sl(400);
                synchronized (TestThread.class) {
                    name = Thread.currentThread().getName();
                }
            }).start();
        }
    }

}
