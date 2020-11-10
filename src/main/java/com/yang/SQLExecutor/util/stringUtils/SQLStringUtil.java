package com.yang.SQLExecutor.util.stringUtils;

import com.yang.SQLExecutor.po.City;
import com.yang.SQLExecutor.table.CityTable;
import com.yang.SQLExecutor.table.TableOperator;
import com.yang.SQLExecutor.util.verUtil.Instanceof;
import org.junit.Test;

import java.util.*;

import static com.yang.SQLExecutor.util.stringUtils.StringUtil.*;

/**
 * @auther YF
 * @create 2020-11-10-20:45
 */
public class SQLStringUtil {

    public static String select = "select ";
    public static String all = " * ";
    public static String from = " from ";
    public static String where = " where ";
    public static String like = " like ";
    public static String update = "update ";
    public static String set = " set ";
    public static String insertInto = "insert into ";
    public static String deleteFrom = "delete from ";
    public static String values = " values ";


    public static String key_tableName = "key_tableName";
    public static String key_setParam = "key_setParam";
    public static String key_whereParam = "key_whereParam";
    public static String key_selectParam = "key_selectParam";
    public static String key_colNames = "key_colNames";
    public static String key_values = "key_values";


    public static String topOfKeyTab = "`";
    public static String notIn = " not in ";
    public static String in = " in ";
    public static String nEqual = " != ";
    public static String percent = "%";
    public static String and = " and ";
    public static String comma = " , ";
    public static String lParentheses = " ( ";
    public static String rParentheses = " ) ";
    public static String equal = " = ";
    public static String gt = " > ";
    public static String gte = " >= ";
    public static String lt = " < ";
    public static String lte = " <= ";
    public static String empty = " ";
    public static String single = "'";

//    private static String listVer(Object)

    public static String buildSelect(Map<String, Object> params) {
        String selectP, tableName, whereP = "";
        Object temp = params.get(key_selectParam);
        Instanceof ele = Instanceof.ele(temp);
        ele.isList(l -> {

        }).isArray(a -> {

        }).isNull(e -> {

        });
        if (temp instanceof List) {
            selectP = join(comma, (List<Object>) temp);
        } else if (temp instanceof String[]) {
            selectP = join(comma, Arrays.asList(temp));
        } else if (temp == null) {
            selectP = all;
        } else {
            throw new RuntimeException("select parameter not match!");
        }
        temp = params.get(key_whereParam);
        if (temp instanceof List) {
            whereP = join(and, (List<Object>) temp);
        } else if (temp instanceof String[]) {
            whereP = join(and, Arrays.asList(temp));
        }
        sb.append(select).append(selectP).append(from).append(topOfKeyTab)
                .append(n2e(params.get(key_tableName))).append(topOfKeyTab)
                .append(prefix(where, whereP));

        return result();
    }

    @Test
    public void test01() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put(key_selectParam, Arrays.asList("1", "", "3"));
                put(key_tableName, "231");
                put(key_whereParam, "  ");
            }
        };
        System.out.println(buildSelect(hashMap));
    }

    @Test
    public void test02() {
        Class<? extends String> aClass = "123".getClass();
        Class<String> stringClass = String.class;
        System.out.println(aClass);
        System.out.println(stringClass);
        System.out.println(aClass.equals(stringClass));
//        Class<? extends CityTable> aClass1 = TableOperator.city.getClass();
//        Class<City> cityClass = City.class;
//        System.out.println(aClass1);
//        System.out.println(cityClass);
    }

    @Test
    public void test03() {
        Instanceof ele = Instanceof.ele("123");
        ele.is(String.class, s -> {
            System.out.println("String => " + s);
        }).isArray(a -> {
            System.out.println("Array => " + a);
        }).isList(l -> {
            System.out.println("List => " + l);
        }).other(o -> {
            System.out.println("other => " + o);
        });
    }


}
