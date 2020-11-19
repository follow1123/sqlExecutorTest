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

    /**
     * 根据不同的关键字拼接条件语句
     *
     * @param colName
     * @param operator
     * @param value
     * @return
     */
    public static String condition(String colName, String operator, String value) {
//        Instanceof ele = Instanceof.ele(value);
//
//        ele.isStr(s -> {
//            if (like.equals(operator)) {
//                s = wrap(percent, s);
//            }
//            return wrap(single, s);
//        });
//
//        if (in.equals(operator) || notIn.equals(operator)) {
//            ele.isList(l -> {
//                for (int i = 0; i < l.size(); i++) {
//                    l.set(i, wrap(single, l.get(i)));
//                }
//                return wrap(lParentheses, rParentheses, join(comma, l));
//            }).isArray(a -> {
//                for (int i = 0; i < a.length; i++) {
//                    a[i] = wrap(single, a[i]);
//                }
//                return wrap(lParentheses, rParentheses, join(comma, Arrays.asList(a)));
//            });
//        }
//
//        String result = ele.other(o -> "").result().toString();
//        if (!"".equals(result)) {
//            sb.append(colName).append(operator).append(result);
//        }
        return result();
    }

    /**
     * 拼接一个select语句
     *
     * @param params
     * @return
     */
    public static String buildSelect(Map<String, String> params) {
//        String selectP, whereP;
//
//        Instanceof ele = Instanceof.ele(params.get(key_selectParam));
//
//        selectP = ele.isList(l -> join(comma, l))
//                .isArray(a -> join(comma, Arrays.asList(a)))
//                .isNull(e -> all)
//                .other(o -> "")
//                .result(String.class);
//
//        whereP = ele.reset(params.get(key_whereParam))
//                .isList(l -> join(and, l))
//                .isArray(a -> join(and, Arrays.asList(a)))
//                .other(o -> "")
//                .result(String.class);
//
//        sb.append(select).append(selectP).append(from).append(topOfKeyTab)
//                .append(n2e(params.get(key_tableName))).append(topOfKeyTab)
//                .append(prefix(where, whereP));

        return result();
    }

    public static String buildSelectParam(List<String> colNames) {
        return join(comma, colNames);
    }

    public static String buildSelectParam(List<String> colNames, List<String> labels) {
        if (colNames == null || labels == null) return "";
        if (colNames.size() != labels.size()) return "";
        for (int i = 0; i < colNames.size(); i++) {
            colNames.set(i, insert(empty, n2et(colNames.get(i)), n2et(labels.get(i))));
        }
        return join(comma, colNames);
    }
}
