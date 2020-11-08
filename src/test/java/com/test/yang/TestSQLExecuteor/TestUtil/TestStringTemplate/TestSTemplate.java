package com.test.yang.TestSQLExecuteor.TestUtil.TestStringTemplate;

import com.yang.SQLExecutor.util.TimeTest;
import com.yang.SQLExecutor.util.stringTemplate.STemplate;
import org.junit.Test;
import sun.misc.Regexp;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther YF
 * @create 2020-11-07-20:48
 */
public class TestSTemplate {

    @Test
    public void test01() {
        String split = "select {sp} from {fp} where {wp}";

        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("sp", "cityName, cityCode");
            put("fp", "`city`");
            put("wp", "cityName = 'Beijing'");
        }};

        TimeTest.testTime(() -> {
            System.out.println(STemplate.render(split, hashMap));
        });

    }
}
