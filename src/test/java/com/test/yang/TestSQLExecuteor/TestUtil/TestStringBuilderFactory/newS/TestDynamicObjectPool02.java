package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory.newS;

import com.yang.SQLExecutor.util.stringUtils.test.newStructure.StringBuilderAdapter;

import java.util.function.Consumer;

import static com.yang.SQLExecutor.util.stringUtils.test.newStructure.DynamicObjectPool.*;

/**
 * @auther YF
 * @create 2020-11-24-19:26
 */
public class TestDynamicObjectPool02 {


    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test01();
        sleep(5000);
        test01();
        sleep(5000);
    }

    private static void startOperation() {
        new Thread(() -> {
            use((Consumer<StringBuilderAdapter>) sb -> {
//                System.out.println("size = " + getSize());
                for (int i1 = 0; i1 < 10; i1++) {
                    sb.append(i1);
                    if (Integer.parseInt(Thread.currentThread().getName().split("-")[1]) % 3 == 0) {
                        sleep(300);
                    }
                }
                if (!"0123456789".equals(sb.toString())) {
                    System.out.println(false);
                }
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

}
