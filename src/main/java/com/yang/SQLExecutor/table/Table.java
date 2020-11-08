package com.yang.SQLExecutor.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auther YF
 * @create 2020-11-02-21:09
 */
public class Table {
    private String tableName;
    public List<String> colNames;

    public Table(String tableName, String... colNames) {
        this.tableName = tableName;
        this.colNames = Arrays.asList(colNames);
    }

    private boolean have(String colName){
        return colNames.contains(colName);
    }


    @Override
    public String toString() {
        return tableName;
    }


}
