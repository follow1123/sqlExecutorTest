package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.yang.SQLExecutor.util.stringUtils.test.StringBuilderFactory.*;

/**
 * @auther YF
 * @create 2020-11-19-20:50
 */
public class StringBuilderFactoryTest {

    public static void main(String[] args) {
        test01();
    }

    public static void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test01() {

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                useBuilder(sb -> {
                    System.out.println("size = " + getBuilders().size());
                    for (int i1 = 0; i1 < 10; i1++) {
                        sb.append(i1);
//                    System.out.println(Thread.currentThread().getName()+ " i = " + i1);
                        if (Integer.parseInt(Thread.currentThread().getName().split("-")[1]) % 3 == 0) {
                            sleep();
                        }
                    }
                    System.out.println(sb.hashCode() + "====" + Thread.currentThread().getName() + "====" + sb);
                });
            }).start();
        }

    }

}
