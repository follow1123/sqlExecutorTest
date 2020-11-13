package com.yang.SQLExecutor.core.param.test;


import java.util.List;

/**
 * @auther YF
 * @create 2020-11-13-21:13
 */
public class Params {
    public static SelectParam colNames(List<String> colNames) {
        return new SelectParam(colNames);
    }

    public static SelectParam namesAndLabels(List<String> colNames, List<String> labels) {
        return new SelectParam(colNames, labels);
    }
}
