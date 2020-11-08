package com.test.yang.TestSQLExecuteor.TestUtil.TestStringTemplate;

import com.yang.SQLExecutor.util.TimeTest;
import com.yang.SQLExecutor.util.stringTemplate.STemplate;
import org.junit.Test;
import sun.misc.Regexp;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther YF
 * @create 2020-11-07-20:48
 */
public class TestSTemplate {


    @Test
    public void test01() {
        String split = "select "
                .concat("@{sp}")
                .concat(" from ")
                .concat("@{fp}")
                .concat(" where ")
                .concat("@{wp:(index = @{i},value = @{v}).j(' and ').f(' where').l(' ; ')} order by cityName");
        System.out.println(split);
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("sp", "cityName, cityCode");
            put("fp", "`city`");
            put("wp", "cityName = 'Beijing'");
        }};

        TimeTest.testTime(() -> {
//            System.out.println(STemplate.render(split, hashMap));
        });

    }

    @Test
    public void test02() {
        Pattern mValue = Pattern.compile("\\@\\{(\\w{1,9})}");

        Pattern mList = Pattern.compile("@\\{(\\w+):\\(([\\S\\s]+)\\)}");

        Matcher matcher = mList.matcher("@{wp:(index = @{i},value = @{v})}");
//        System.out.println(matcher.groupCount());

        String[] arr = {"a", "b", "c", "d"};

        HashMap<String, Object> map = new HashMap<>();

        map.put("wp", arr);
        while (matcher.find()) {
            Object o = map.get(matcher.group(1));
            List<String> value = null;
            HashMap<String, Object> tempMap = new HashMap<>();
            if (o instanceof String[]) {
                value = Arrays.asList((String[]) o);
            } else if (o instanceof List) {
                value = (List<String>) o;
            }
            for (int i = 0; i < value.size(); i++) {
                tempMap.put("i", i);
                tempMap.put("v", value.get(i));
                Matcher matcher1 = mValue.matcher(matcher.group(2));
                while (matcher1.find()) {
                    System.out.println(matcher1.group(1));
                }
            }
        }

    }

    @Test
    public void test03() {
        String str = "@{wp:(index = @{i},value = @{v}).j(' and ').f(' where').l(' ; ')}";
        Pattern compile = Pattern.compile("@\\{(\\w+):\\(([\\w =@\\{},-]+)\\)([\\S\\s]+)}");
        Pattern compile1 = Pattern.compile("\\.([jlf])\\('([\\w ;]+)'\\)");
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
//            parseListMarcher(matcher);
//            Matcher matcher1 = compile1.matcher(matcher.group(3));
//            while (matcher1.find()) {
//                System.out.println(matcher1.group(1));
//                System.out.println(matcher1.group(2));
//            }
        }
    }

    @Test
    public void test05() {
        List<String> strings = Arrays.asList("1", "2", "3");
        String str = "@{wp:(@{i} = @{v}).j(' and ').f(' where ').l(' ; ')}";
        HashMap<String, Object> wp = new HashMap<String, Object>() {{
            put("wp", strings);
        }};
        Pattern compile = Pattern.compile("@\\{(\\w+):\\(([\\w =@\\{},-]+)\\)([\\S\\s]+)}");
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
//            parseListMarcher(matcher, wp);
        }
    }

    @Test
    public void test07() {
        String[] a1 = {"b", "c", "d"};
        String[] a2 = new String[5];
        a2[0] = "a";
        a2[a2.length - 1] = "e";
        System.arraycopy(a1, 0, a2, 1, a1.length);
        System.out.println(Arrays.asList(a1));
        System.out.println(Arrays.asList(a2));
    }

    @Test
    public void test06() {
        String s = "a";
        System.out.println(s);
        s.concat("b");
        System.out.println(s);
    }

    @Test
    public void test08() {
        String str = "select @{sp:(@{v}).j(', ')} from `@{fp}` @{wp:(@{k} = '@{v}').f('where ').j(' and ')}";
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put("sp", Arrays.asList("CityName", "CityCode"));
                put("fp", "city");
                put("wp", new HashMap<String, Object>() {
                    {
                        put("cityName", "Beijing");
                        put("cityCode", "PEK");
                    }
                });
            }
        };

        TimeTest.testTime(() -> {
            System.out.println(STemplate.render(str, hashMap));
        });
    }

    @Test
    public void test09(){
       String sqlTemplate = "update `@{tableName}` @{sp:(@{k} = '@{v}').j(', ').f('set ')} @{wp:(@{k} = '@{v}').j(' and ').f('where ')}";

        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put("tableName", "city");
                put("sp", new HashMap<String, Object>(){{
                    put("CityName", "BJ");
                }});

//                put("wp", new HashMap<String, Object>() {
//                    {
//                        put("cityCode", "PEK");
//                    }
//                });
            }
        };

        System.out.println(STemplate.render(sqlTemplate, hashMap));

    }

    @Test
    public void test10(){
       String sql = "insert into `@{tableName}` @{colNames:(@{v}).f(' (').l(') ').j(', ')} values @{values:('@{v}').f(' (').l(') ').j(', ')}";

        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put("tableName", "city");
                put("colNames", Arrays.asList("CityName", "CityCode", "CountryCode"));
                put("values", Arrays.asList("Wuhan", "TH", "CN"));
            }
        };


        System.out.println(STemplate.render(sql, hashMap));
        String s = "(?<=@{)(\\w{0,9})(?=})";
    }

    @Test
    public void test11(){
           String str ="@{map}.for(@{v1}.for(@{k2} @{k1} @{v2}.j(12))).j(1).f(j)";
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put("name", "city");
                put("arr", Arrays.asList(Arrays.asList("CityName", "CityCode", "CountryCode")));
                put("map", Arrays.asList("Wuhan", "TH", "CN"));
            }
        };
    }
}
