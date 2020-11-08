package com.yang.SQLExecutor.core.param.operator;

import com.yang.SQLExecutor.core.param.SelectParam;
import com.yang.SQLExecutor.core.param.WhereParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-07-16:43
 */
public class SQLOperator {


    public static SelectParam all = SelectOperator::all;

    public static SelectParam names(String... names) {
        return so -> so.names(names);
    }

    public static SelectParam names(List<String> names) {
        return so -> so.names(names);
    }

    public static SelectParam namesAsLabel(String... nameAndLabel) {
        return so -> so.namesAsLabels(nameAndLabel);
    }

    public static SelectParam namesAsLabel(Map<String, String> nameAndLabel) {
        return so -> so.namesAsLabels(nameAndLabel);
    }


    public static WhereParam equal(String name, String value) {
        return wo -> wo.operator(equal, name, value);
    }

    public static WhereParam like(String columnName, String value) {
        return wo -> wo.operator(like, columnName, value);
    }

    public static WhereParam in(String columnName, String... values) {
        return wo -> wo.operator(in, true, columnName, wrappedInBrackets(false, values));
    }

    public static WhereParam in(String columnName, List<String> values) {
        return wo -> wo.operator(in, true, columnName, wrappedInBrackets(false, values));
    }


    public static WhereParam bt(String columnName, String value) {
        return wo -> wo.operator(gt, columnName, value);
    }

    public static WhereParam bte(String columnName, String value) {
        return wo -> wo.operator(gte, columnName, value);
    }

    public static WhereParam st(String columnName, String value) {
        return wo -> wo.operator(lt, columnName, value);
    }

    public static WhereParam ste(String columnName, String value) {
        return wo -> wo.operator(lte, columnName, value);
    }

    public static WhereParam not(String columnName, String value) {
        return wo -> wo.operator(nEqual, columnName, value);
    }

    public static WhereParam notIn(String columnName, String... values) {
        return wo -> wo.operator(notIn, true, columnName, wrappedInBrackets(false, values));
    }

    public static WhereParam notIn(String columnName, List<String> values) {
        return wo -> wo.operator(notIn, true, columnName, wrappedInBrackets(false, values));
    }

}
