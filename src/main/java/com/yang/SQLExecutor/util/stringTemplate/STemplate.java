package com.yang.SQLExecutor.util.stringTemplate;

import com.yang.SQLExecutor.util.TimeTest;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther YF
 * @create 2020-11-07-20:45
 * 字符串模板工具
 */
public class STemplate {

    //匹配普通值的正则表达式
    private static Pattern valueReg = Pattern.compile("\\@\\{(\\w{1,9})}");

    //匹配集合的正则表达式
    private static Pattern collectionReg = Pattern.compile("@\\{(\\w+):\\(([\\w =@\\{}',-]+)\\)([\\.\\w '\\(\\);,:]+)}");
    //匹配集合内前缀后缀的正则
    private static Pattern collectionArgReg = Pattern.compile("\\.([jlf])\\(([\\w ;,:\\)\\(]+)\\)");

    //StringBuilder
    private static StringBuilder sb = new StringBuilder();
    //临时的map主要存数组的下标及对应的值或map的key和value
    private static Map<String, Object> tempMap = new HashMap<>();
    //临时参数储存主要储存集合内前缀后缀的值
    private static Map<String, Object> paramMap = new HashMap<>();
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
     * 返回当前建造器里面的字符串并清空字符串建造器
     *
     * @return
     */
    private static String value() {
        String value = sb.toString();
        sb.delete(0, sb.length());
        return value;
    }

    /**
     * 存数组的下标及对应的值
     * @param index
     * @param value
     */
    public static void putListValue(int index, Object value) {
        tempMap.put("i", index);
        tempMap.put("v", value);
    }

    /**
     * 存map的key和value
     * @param key
     * @param value
     */
    public static void putMapValue(String key, Object value) {
        tempMap.put("k", key);
        tempMap.put("v", value);
    }

    /**
     * 清空临时map
     */
    public static void clearMap() {
        tempMap.clear();
    }

    /**
     * 对象为空这置为空字符串
     * @param o
     * @return
     */
    public static String nullToEmpty(Object o) {
        return o == null ? "" : o.toString();
    }

    /**
     * 渲染普通值得模板字符串
     * @param target
     * @param value
     * @return
     */
    public static String renderString(String target, Map<String, Object> value) {
        String[] split = target.split(valueReg.toString());
        Matcher matcher = valueReg.matcher(target);
        String[] sp2 = new String[split.length + 1];
        sp2[sp2.length - 1] = "";
        System.arraycopy(split, 0, sp2, 0, split.length);

        for (int i = 0; i < sp2.length; i++) {
            sb.append(sp2[i]).append(matcher.find() ? nullToEmpty(value.get(matcher.group(1))) : "");
        }
        return value();
    }

    /**
     * 渲染list的模板
     * @param target
     * @param values
     * @return
     */
    public static String renderList(String target, List<Object> values) {
        String first = nullToEmpty(paramMap.get("f"));
        String last = nullToEmpty(paramMap.get("l"));
        String join = nullToEmpty(paramMap.get("j"));
        sb.append(first);
        for (int i = 0; i < values.size(); i++) {
            putListValue(i, values.get(i));
            sb.append(renderString(target, tempMap)).append(join);
        }
        replace(join.length(), last);
        clearMap();
        return value();
    }

    /**
     * 渲染map的模板
     * @param target
     * @param values
     * @return
     */
    public static String renderMap(String target, Map<String, Object> values) {
        String first = nullToEmpty(paramMap.get("f"));
        String last = nullToEmpty(paramMap.get("l"));
        String join = nullToEmpty(paramMap.get("j"));
        sb.append(first);
        values.forEach((k, v) -> {
            putMapValue(k, v);
            sb.append(renderString(target, tempMap)).append(join);
        });
        replace(join.length(), last);
        clearMap();
        return value();
    }

    /**
     * 渲染传入的模板字符串
     * @param target
     * @param value
     * @return
     */
    public static String render(String target, Map<String, Object> value) {
        String[] split = target.split(collectionReg.toString());
        Matcher matcher = collectionReg.matcher(target);
        for (String s : split) {
            sb.append(renderString(s, value));
            if (matcher.find()) {
                String param = matcher.group(3);
                if (!"".equals(nullToEmpty(param))) {
                    putParamToMap(param);
                }

                String key = matcher.group(1);
                String valueStr = matcher.group(2);
                Object v;
                if ((v = value.get(key)) instanceof List) {
                    sb.append(renderList(valueStr, (List) v));
                } else if ((v = value.get(key)) instanceof Object[]) {
                    sb.append(renderList(valueStr, Arrays.asList((Object[]) v)));
                } else if ((v = value.get(key)) instanceof Map) {
                    sb.append(renderMap(valueStr, (Map) v));
                }
            }
        }

        return value();
    }

    /**
     * 将集合内前缀后缀参数存入临时map
     * @param param
     */
    public static void putParamToMap(String param) {
        Matcher matcher = collectionArgReg.matcher(param);
        paramMap.clear();
        while (matcher.find()) {
            paramMap.put(matcher.group(1), matcher.group(2));
        }
    }

}
