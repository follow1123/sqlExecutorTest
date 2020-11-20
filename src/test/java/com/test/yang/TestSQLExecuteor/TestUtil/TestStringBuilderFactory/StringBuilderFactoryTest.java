package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory;

import com.yang.SQLExecutor.util.stringUtils.test.StringBuilderAdapter;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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

    @Test
    public void test02() {

        StringBuilderAdapter sba = new StringBuilderAdapter(new StringBuilder());

        String and = " and ", param = " param = value ";

        for (int i = 0; i < 5; i++) {
            sba.append(param).append(and);
        }
        sba.replaceLastEmpty(and.length());
        System.out.println(sba);


    }

    public static void test01() {
        for (int j = 10; j > 0; j--) {

            for (int i = 0; i < j * 10; i++) {
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

}
