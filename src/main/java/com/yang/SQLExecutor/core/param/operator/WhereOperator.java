package com.yang.SQLExecutor.core.param.operator;

import com.yang.SQLExecutor.core.param.WhereParam;

import java.util.ArrayList;
import java.util.List;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-03-20:37
 */
public class WhereOperator {
    private static WhereOperator instance;

    private List<String> values;

    private WhereOperator() {
        values = new ArrayList<>();
    }


    public static WhereOperator getInstance() {
        if (instance == null) {
            instance = new WhereOperator();
        }
        return instance;
    }

    public String operator(String ope, boolean noSingle, String columnName, Object value) {
        return insertSymbol(ope, noSingle, columnName, value);
    }

    public String operator(String ope, String columnName, Object value) {
        return operator(ope, false, columnName, value);
    }

}
