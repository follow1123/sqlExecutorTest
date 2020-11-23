package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory.newS;

import com.yang.SQLExecutor.util.stringUtils.test.newStructure.StringBuilderAdapter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.yang.SQLExecutor.util.stringUtils.test.newStructure.DynamicObjectPool.*;

/**
 * @auther YF
 * @create 2020-11-21-21:19
 */
public class TestDynamicObjectPool {


    public static void main(String[] args) {
//
//        long start = System.nanoTime();
//
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        long end = System.nanoTime();
//
//        System.out.println((end - start));
//        test06();
    }


    public static void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startOperation() {
        new Thread(() -> {
            use((Consumer<StringBuilderAdapter>) sb -> {
                System.out.println("size = " + getSize());
                for (int i1 = 0; i1 < 10; i1++) {
                    sb.append(i1);
                    if (Integer.parseInt(Thread.currentThread().getName().split("-")[1]) % 3 == 0) {
                        sleep(300);
                    }
                }
                System.out.println("0123456789".equals(sb.toString()));
            });
        }).start();
    }

    //测试线程由多到少
    private static void test01() {
        for (int j = 10; j > 0; j--) {
            for (int i = 0; i < j * 10; i++) {
                startOperation();
            }
        }
    }

    //测试线程由少到多
    public static void test02() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < i * 10; j++) {
                startOperation();
            }
        }
    }

    //测试线程多的境况
    public static void test03() {
        for (int i = 0; i < 1000; i++) {
            startOperation();
        }
    }

    //测试线程少的境况
    public static void test04() {
        for (int i = 0; i < 10; i++) {
            startOperation();
        }
    }

    @Test
    public void testOther() {

//        System.out.println(System.currentTimeMillis());
//        System.out.println(System.nanoTime());

    System.out.println(TimeUnit.SECONDS.toNanos(2));


    }

    public static void test05() {

        new Thread(() -> {
            StringBuilderAdapter sb = (StringBuilderAdapter) listenerAndGet();
            sb.append(Thread.currentThread().getName() + " 执行操作！");
            System.out.println(sb);
            sb.recycle();
        }).start();

        sleep(6000);
        new Thread(() -> {
            StringBuilderAdapter sb = (StringBuilderAdapter) listenerAndGet();
            sb.append(Thread.currentThread().getName() + " 执行操作！");
            System.out.println(sb);
            sb.recycle();
        }).start();

    }

    public static void test06() {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                if (Integer.parseInt(Thread.currentThread().getName().split("-")[1]) % 3 == 0) {
                    sleep(5500);
                }
                use((Consumer<StringBuilderAdapter>) sb -> {
                    System.out.println("size = " + getSize());
                    for (int i1 = 0; i1 < 10; i1++) {
                        sb.append(i1);
                    }
//                    System.out.println("0123456789".equals(sb.toString()));
                });
            }).start();
        }
    }
}
