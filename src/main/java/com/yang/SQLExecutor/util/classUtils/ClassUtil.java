package com.yang.SQLExecutor.util.classUtils;

import java.util.List;
import java.util.Map;

import static com.yang.SQLExecutor.util.classUtils.Symbol.*;

/**
 * @auther YF
 * @create 2020-11-07-17:59
 */
public class ClassUtil {

    public static void buildClass(String path) {

    }


    private static StringBuilder sb = new StringBuilder();


    private static String clear() {
        String s = sb.toString();
        sb.delete(0, sb.length());
        return s;
    }

    public static String setDefault(String target, String defaultStr) {
        return target == null || target.trim().isEmpty() ? defaultStr : target;
    }

    public static String emptyDefault(String target) {
        return setDefault(target, "");
    }


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
        replace(lastLength, "");
    }

    public static String buildFieldStr(String modifier, String status, String type, String name) {
        sb.append(setDefault(modifier, _private)).append(empty)
                .append(emptyDefault(status)).append(empty)
                .append(setDefault(type, _string)).append(empty)
                .append(name).append(semicolon);
        return clear();
    }

    public static String buildMethodStr(String modifier, String status, String returnType, String name, List<String> params, List<String> contents) {
        sb.append(tab).append(setDefault(modifier, _public)).append(empty)
                .append(emptyDefault(status)).append(empty)
                .append(setDefault(returnType, _void)).append(empty)
                .append(name).append(lParentheses).append(empty);
        params.forEach(v -> sb.append(v).append(comma));
        replace(1);
        sb.append(rParentheses).append(opening).append(nextLine);
        contents.forEach(v -> sb.append(tab).append(tab).append(v).append(nextLine));
        sb.append(tab).append(closing);
        return clear();
    }


    public static String buildClassStr(String modifier, String name,
                                       String type, String status,
                                       String aPackage, String aExtends,
                                       List<String> impls,
                                       List<String> imports,
                                       List<FieldModel> fieldModels,
                                       List<MethodModel> methodModels,
                                       List<ConstructorModel> constructorModels) {

        if (aPackage != null) {
            sb.append(_package).append(empty).append(aPackage).append(semicolon).append(nextLine);
        }
        sb.append(nextLine);
        if (imports.size() > 0) {
            imports.forEach(v -> {
                sb.append(_import).append(empty).append(v).append(semicolon).append(nextLine);
            });
        }
        sb.append(nextLine);
        sb.append(nextLine);
        sb.append(setDefault(modifier, _public)).append(empty)
                .append(emptyDefault(status)).append(empty)
                .append(setDefault(type, _class)).append(empty)
                .append(name).append(empty);
        if (aExtends != null){
            sb.append(_extends).append(empty).append(aExtends).append(empty);
        }
        if (impls.size() > 0){
            sb.append(_implements).append(empty);
            impls.forEach(v->{
                sb.append(v).append(comma);
            });
            replace(1);
        }
        sb.append(opening).append(nextLine);

        sb.append(nextLine);
        if (fieldModels.size() > 0){
            fieldModels.forEach(v->{
                sb.append(tab).append(v.getModel()).append(nextLine);
            });
        }
        sb.append(nextLine);
        if (constructorModels.size() > 0){
            constructorModels.forEach(v->{
                sb.append(v.getModel()).append(nextLine).append(nextLine);
            });
        }
        sb.append(nextLine);
        if (methodModels.size() > 0){
            methodModels.forEach(v->{
                sb.append(v.getModel()).append(nextLine).append(nextLine);
            });
        }
        sb.append(closing);
        return clear();
    }

}
