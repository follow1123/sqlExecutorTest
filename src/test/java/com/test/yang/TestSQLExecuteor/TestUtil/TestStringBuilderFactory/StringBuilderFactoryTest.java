package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory;

import com.yang.SQLExecutor.util.TimeTest;

import static com.yang.SQLExecutor.util.stringUtils.test.StringBuilderFactory.*;

/**
 * @auther YF
 * @create 2020-11-19-20:50
 */
public class StringBuilderFactoryTest {

    public static void main(String[] args) {
        test04();
    }

    public static void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startOperation() {
        new Thread(() -> {
            System.out.println("--------------------------------------------------------" + TimeTest.testTime(() -> {
                useBuilder(sb -> {
                    System.out.println("size = " + getBuilders().size());
                    for (int i1 = 0; i1 < 10; i1++) {
                        sb.append(i1);
                        if (Integer.parseInt(Thread.currentThread().getName().split("-")[1]) % 3 == 0) {
                            sleep();
                        }
                    }
                    System.out.println(sb.hashCode() + "====" + Thread.currentThread().getName() + "====" + sb);
                });
            }));
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

}
