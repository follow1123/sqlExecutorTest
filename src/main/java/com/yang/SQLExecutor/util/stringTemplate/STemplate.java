package com.yang.SQLExecutor.util.stringTemplate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther YF
 * @create 2020-11-07-20:45
 */
public class STemplate {

    private static Pattern valueReg =  Pattern.compile("\\{(\\w{1,9})}");

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
     * 返回当前建造器里面的字符串并清空字符串建造器
     *
     * @return
     */
    private static String value() {
        String value = sb.toString();
        sb.delete(0, sb.length());
        return value;
    }

    public static String render(String str, Map<String, String> value){
        String[] split = str.split(valueReg.toString());
        Matcher matcher = valueReg.matcher(str);
        for (int i = 0; i < split.length && matcher.find(); i++) {
            sb.append(split[i]).append(value.get(matcher.group(1)));
        }
        return value();
    }


}
