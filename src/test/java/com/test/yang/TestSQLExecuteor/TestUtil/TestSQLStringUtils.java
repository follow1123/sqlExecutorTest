package com.test.yang.TestSQLExecuteor.TestUtil;

import org.junit.Test;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;
/**
 * @auther YF
 * @create 2020-11-05-21:34
 */
public class TestSQLStringUtils {

    @Test
    public void test01(){
        System.out.println(connectWithSymbol(comma,  "b", "c", "d"));
        System.out.println(insertSymbol(equal, true,"name", "kkqe"));
    }

    @Test
    public void test02(){
        System.out.println(wrappedInBrackets("1231", "12312", "213123"));
    }
}
