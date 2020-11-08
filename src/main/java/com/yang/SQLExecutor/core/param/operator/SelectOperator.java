package com.yang.SQLExecutor.core.param.operator;

import com.yang.SQLExecutor.core.sql.sqlInterface.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-04-20:24
 */
public class SelectOperator {


    private static SelectOperator instance;

    private ArrayList<String> values;

    private SelectOperator() {
        values = new ArrayList<>();
    }

    public static SelectOperator getInstance() {
        if (instance == null) {
            instance = new SelectOperator();
        }
        return instance;
    }

    public String all() {
        return all;
    }

    public String names(String... names) {
        return names(Arrays.asList(names));
    }

    public String names(List<String> names) {
        return connectWithComma(true, names);
    }


    public String namesAsLabels(String... nameAndLabel) {
        if (null == nameAndLabel || nameAndLabel.length % 2 == 1) return "";
        values.clear();
        for (int i = 0; i < nameAndLabel.length; i += 2) {
            values.add(insertEmpty(nameAndLabel[i], nameAndLabel[i + 1]));
        }
        return connectWithComma(true, values);
    }

    public String namesAsLabels(Map<String, String> nameAndLabel) {
        values.clear();
        nameAndLabel.forEach((k, v) -> {
            values.add(insertEmpty(k, v));
        });
        return connectWithComma(true, values);
    }
}
