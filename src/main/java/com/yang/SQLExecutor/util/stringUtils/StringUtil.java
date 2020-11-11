package com.yang.SQLExecutor.util.stringUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @auther YF
 * @create 2020-11-10-20:25
 */
public class StringUtil {
    public static StringBuilder sb = new StringBuilder();

    /**
     * 替换倒数长度的字符串
     *
     * @param lastLength
     * @param str
     */
    public static void replace(int lastLength, Object str) {
        sb.replace(sb.length() - lastLength, sb.length(), n2e(str));
    }

    /**
     * 替换倒数长度的字符串,默认替换为空字符
     *
     * @param lastLength
     */
    public static void replace(int lastLength) {
        replace(lastLength, "");
    }

    /**
     * 判断是否为null是则返回空串
     *
     * @param str
     * @return
     */
    public static String n2e(Object str) {
        return str == null ? "" : str.toString();
    }

    /**
     * 空字符串建造器
     */
    public static void clear() {
        sb.delete(0, sb.length());
    }

    /**
     * 返回当前建造器里面的字符串并清空字符串建造器
     *
     * @return
     */
    public static String result() {
        String value = sb.toString();
        clear();
        return value;
    }

    /**
     * 将一个集合使用一个符号连接起来
     *
     * @param symbol
     * @param strings
     * @return
     */
    public static String join(String symbol, List<Object> strings) {
        String[] cur = new String[1];
        strings.forEach(v -> {
            if (!"".equals(cur[0] = n2e(v))) {
                sb.append(cur[0]).append(symbol);
            }
        });
        replace(symbol.length());
        return result();
    }

    /**
     * 使用两个符号包裹一个对象
     *
     * @param symbolStart
     * @param symbolEnd
     * @param target
     * @return
     */
    public static String wrap(String symbolStart, String symbolEnd, Object target) {
        String cur;
        if (!"".equals(cur = n2e(target))) {
            sb.append(n2e(symbolStart)).append(cur).append(n2e(symbolEnd));
        }
        return result();
    }
    /**
     * 使用一个符号包裹一个对象
     *
     * @param symbol
     * @param target
     * @return
     */
    public static String wrap(String symbol, Object target) {
        return wrap(symbol, symbol, target);
    }

    /**
     * 添加前缀
     *
     * @param symbol
     * @param target
     * @return
     */
    public static String prefix(String symbol, Object target) {
        return wrap(symbol, null, target);
    }

    /**
     * 添加后缀
     *
     * @param symbol
     * @param target
     * @return
     */
    public static String suffix(String symbol, Object target) {
        return wrap(null, symbol, target);
    }

}
