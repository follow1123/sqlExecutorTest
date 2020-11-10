package com.yang.SQLExecutor.util;

import com.yang.SQLExecutor.util.stringTemplate.STemplate;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-05-21:13
 */
public class SQLStringUtils {

    public static String select = "select ";
    public static String from = " from ";
    public static String update = "update ";
    public static String insertInto = "insert into ";
    public static String deleteFrom = "delete from ";
    public static String topOfKeyTab = "`";
    public static String values = " values ";






    public static String key_tableName = "tableName";
    public static String key_setParam = "setParam";
    public static String key_whereParam = "whereParam";
    public static String key_selectParam = "selectParam";
    public static String key_colNames = "colNames";
    public static String key_values = "values";

    private static String selectTemplate = "select @{selectParam:(@{v}).j(, )} from `@{tableName}` @{whereParam:(@{v}).f(where ).j( and )}";

    public static String set = " set ";
    public static String and = " and ";
    public static String in = " in ";
    public static String notIn = " not in ";
    public static String nEqual = " != ";

    public static String orderBy = " order by ";

    public static String where = " where ";

    public static String like = " like ";
    public static String all = " * ";


    public static String percent = "%";

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

//    private static String
//    private static String
//    private static String
//    private static String


    private static StringBuilder sb = new StringBuilder();


    /**
     * 替换倒数长度的字符串
     *
     * @param lastLength
     * @param str
     */
    private static void replace(int lastLength, String str) {
        sb.replace(sb.length() - lastLength, sb.length(), str);
    }

    /**
     * 替换倒数长度的字符串,默认替换为空字符
     *
     * @param lastLength
     */
    private static void replace(int lastLength) {
        replace(lastLength, " ");
    }


    /**
     * 空字符串建造器
     */
    private static void clear() {
        sb.delete(0, sb.length());
    }

    /**
     * 返回当前建造器里面的字符串并清空字符串建造器
     *
     * @return
     */
    private static String value() {
        String value = sb.toString();
        clear();
        return value;
    }


    /**
     * 使用一个符号进行连接
     *
     * @param symbol
     * @param noSingle
     * @param str1
     * @param str2
     * @return
     */
    public static String insertSymbol(String symbol, boolean noSingle, String str1, Object str2) {
        if ((str1 == null || str1.trim().isEmpty()) ||
                (str2 == null || str2.toString().trim().isEmpty())) return "";
        String cur = noSingle ? "" : single;
        sb.append(str1).append(symbol)
                .append(cur).append(str2).append(cur);
        return value();
    }

    /**
     * 重载默认值使用单引号包裹
     *
     * @param symbol
     * @param str1
     * @param str2
     * @return
     */
    public static String insertSymbol(String symbol, String str1, Object str2) {
        return insertSymbol(symbol, false, str1, str2);
    }

    public static String insertEmpty(String str1, Object str2) {
        return insertSymbol(empty, true, str1, str2);
    }

    public static String insertEqual(String str1, Object str2) {
        return insertSymbol(equal, str1, str2);
    }

    /**
     * 将一个字符串集合使用一个符号进行连接
     *
     * @param symbol
     * @param noSingle
     * @param values
     * @return
     */
    public static String connectWithSymbol(String symbol, boolean noSingle, String... values) {
        return connectWithSymbol(symbol, noSingle, Arrays.asList(values));
    }

    /**
     * 重载默认使用单引号包裹
     *
     * @param symbol
     * @param values
     * @return
     */
    public static String connectWithSymbol(String symbol, String... values) {
        return connectWithSymbol(symbol, false, values);
    }

    /**
     * 重载list传参
     *
     * @param symbol
     * @param noSingle
     * @param values
     * @return
     */
    public static String connectWithSymbol(String symbol, boolean noSingle, List<String> values) {
        String cur = noSingle ? "" : single;
        values.forEach(v -> {
            sb.append(cur).append(v).append(cur).append(symbol);
        });
        replace(symbol.length());
        return value();
    }

    /**
     * 重载
     *
     * @param symbol
     * @param values
     * @return
     */
    public static String connectWithSymbol(String symbol, List<String> values) {
        return connectWithSymbol(symbol, false, values);
    }

    /**
     * 使用逗号连接
     *
     * @param noSingle
     * @param values
     * @return
     */
    public static String connectWithComma(boolean noSingle, List<String> values) {
        return connectWithSymbol(comma, noSingle, values);
    }

    /**
     * 重载
     *
     * @param noSingle
     * @param values
     * @return
     */
    public static String connectWithComma(boolean noSingle, String... values) {
        return connectWithSymbol(comma, noSingle, values);
    }

    /**
     * 重载，默认使用单引号包裹
     *
     * @param values
     * @return
     */
    public static String connectWithComma(List<String> values) {
        return connectWithComma(false, values);
    }

    /**
     * 重载
     *
     * @param values
     * @return
     */
    public static String connectWithComma(String... values) {
        return connectWithComma(false, values);
    }

    /**
     * 重载，值默认使用单引号包裹
     *
     * @param values
     * @return
     */
    public static String wrappedInBrackets(String... values) {
        return wrappedInBrackets(false, values);
    }

    /**
     * 默认使用逗号连接，使用括号包裹
     *
     * @param noSingle
     * @param values
     * @return
     */
    public static String wrappedInBrackets(boolean noSingle, String... values) {
        sb.append(lParentheses).append(connectWithComma(noSingle, values)).append(rParentheses);
        return value();
    }


    /**
     * 重载，值默认使用单引号包裹
     *
     * @param values
     * @return
     */
    public static String wrappedInBrackets(List<String> values) {
        return wrappedInBrackets(false, values);
    }

    /**
     * 默认使用逗号连接，使用括号包裹
     *
     * @param noSingle
     * @param values
     * @return
     */

    public static String wrappedInBrackets(boolean noSingle, List<String> values) {
        sb.append(lParentheses).append(connectWithComma(noSingle, values)).append(rParentheses);
        return value();
    }


    /**
     * 判断是否为null是则返回空串
     *
     * @param str
     * @return
     */
    private static String nullToEmpty(Object str) {
        return str == null ? "" : str.toString();
    }

    /**
     * 将表格名左右加上 ` 符号
     *
     * @param tableName
     * @return
     */
    public static String wrappedAsTable(String tableName) {
        sb.append(topOfKeyTab).append(tableName).append(topOfKeyTab);
        return value();
    }

    /**
     * 根据sqlMap构建一条insert语句
     *
     * @param sqlMap
     * @return
     */
    public static String buildInsert(Map<String, Object> sqlMap) {
        String tableName = nullToEmpty(sqlMap.get(key_tableName));
        String colNames = nullToEmpty(sqlMap.get(key_colNames));
        String values = nullToEmpty(sqlMap.get(key_values));
        sb.append(insertInto).append(empty).append(tableName).append(empty)
                .append(colNames).append(empty).append(values);
        return value();
    }

    /**
     * 根据sqlMap构建一条update语句
     *
     * @param sqlMap
     * @return
     */
    public static String buildUpdate(Map<String, Object> sqlMap) {
        String tableName = nullToEmpty(sqlMap.get(key_tableName));
        String setParam = nullToEmpty(sqlMap.get(key_setParam));
        String whereParam = nullToEmpty(sqlMap.get(key_whereParam));

        sb.append(update).append(empty)
                .append(tableName).append(empty)
                .append(set).append(setParam)
                .append(empty).append(whereParam);
        return value();
    }

    /**
     * 根据sqlMap构建一条Delete语句
     *
     * @param sqlMap
     * @return
     */
    public static String buildDelete(Map<String, Object> sqlMap) {
        String tableName = nullToEmpty(sqlMap.get(key_tableName));
        String whereParam = nullToEmpty(sqlMap.get(key_whereParam));
        sb.append(deleteFrom).append(empty).append(tableName).append(empty)
                .append(whereParam);
        return value();
    }

    /**
     * 根据sqlMap构建一条select语句
     *
     * @param sqlMap
     * @return
     */
    public static String buildSelect(Map<String, Object> sqlMap) {
        String render = STemplate.render(selectTemplate, sqlMap);
        return render;
    }
    @Test
    public void test01(){
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put("tableName", "city");
                put("selectParam", Arrays.asList("*"));
                put("whereParam", Arrays.asList("CityName = 'Beijing'", "CityCode = 'PEK'"));
            }
        };
        System.out.println(buildSelect(hashMap));
    }

    @Test
    public void test02(){
       String sql = "@{column} @{ope} '@{value}'";
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {
            {
                put("column", "CityName");
                put("ope", "=");
                put("value", "Beijing");
            }
        };
        TimeTest.testTime(()->{

        System.out.println(STemplate.render(sql, hashMap));
       });
        TimeTest.testTime(()->{
            System.out.println(insertSymbol("=", "CityName", "Beijing"));
        });
    }
}
