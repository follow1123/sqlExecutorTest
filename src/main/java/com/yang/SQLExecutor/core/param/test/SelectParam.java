package com.yang.SQLExecutor.core.param.test;

import com.yang.SQLExecutor.table.Table;

import java.util.List;
import java.util.stream.Collectors;

import static com.yang.SQLExecutor.util.stringUtils.SQLStringUtil.*;

/**
 * @auther YF
 * @create 2020-11-13-20:34
 */
public class SelectParam {

    private List<String> colNames;

    private List<String> labels;

    public SelectParam(List<String> colNames) {
        this.colNames = colNames;
    }

    public SelectParam(List<String> colNames, List<String> labels) {
        this(colNames);
        this.labels = labels;
    }

    public String getParamByTable(Table table) {
        if (labels == null) {
            return buildSelectParam(colNames.stream().filter(table::have).collect(Collectors.toList()));
        } else {
            return "";
        }
    }
}
