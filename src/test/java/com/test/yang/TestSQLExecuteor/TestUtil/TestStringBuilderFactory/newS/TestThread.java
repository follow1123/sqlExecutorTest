package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory.newS;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther YF
 * @create 2020-11-22-13:54
 */
public class TestThread {

    private volatile static long curNanoSecond;

    static Thread thread = new Thread(() -> {
        long preNS;
        while (true) {
            if (curNanoSecond != 0) {
                preNS = curNanoSecond;
                System.out.println("marked nano is " + preNS);
                sl(1000);
                if (isSameTime(preNS)) {
                    System.out.println("same");
                }
            }
        }
    });

    private synchronized static boolean isSameTime(long ns) {
        return ns == curNanoSecond;
    }

    static {
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
        test03();
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
//                if (curNanoSecond != null && !preName.equals(curNanoSecond)) {
//                    System.out.println((preName = curNanoSecond));
//                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    public static void test02() {

//        for (int i = 0; i < 50; i++) {
//            new Thread(() -> {
//                sl(400);
//                synchronized (TestThread.class) {
//                    curNanoSecond = Thread.currentThread().getName();
//                }
//            }).start();
//        }
    }

    public static void test03() {
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                sl(1500);
            }
            new Thread(() -> {
                synchronized (TestThread.class) {
                    System.out.println(curNanoSecond);
                    System.out.println(Thread.currentThread().getName() + " change the value!");
                    curNanoSecond = System.nanoTime();
                    System.out.println(curNanoSecond);
                }
            }).start();
        }
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

}
