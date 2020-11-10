package com.yang.SQLExecutor.core.sql.sqlInterface.impl;

import com.yang.SQLExecutor.core.sql.sqlInterface.Insert;
import com.yang.SQLExecutor.mapper.GeneralMapper;
import com.yang.SQLExecutor.util.SQLStringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-05-20:12
 */
public class InsertImpl extends BaseSQLImpl implements Insert {
    private GeneralMapper generalMapper;

    public InsertImpl(GeneralMapper generalMapper) {
        this.generalMapper = generalMapper;
    }

    public InsertImpl() {
        super();
    }

    /**
     * 添加表名
     * @param tableName
     * @return
     */
    @Override
    public Insert insert(String tableName) {
        putTableName(tableName);
        return this;
    }

    /**
     * 添加字段名
     * @param colNames
     * @return
     */
    @Override
    public Insert names(String... colNames) {
        names(Arrays.asList(colNames));
        return this;
    }

    /**
     * 添加字段名对应的值
     * @param values
     * @return
     */
    @Override
    public Insert values(String... values) {
        values(Arrays.asList(values));
        return this;
    }

    /**
     * 重载，添加字段名，list传参
     * @param colNames
     * @return
     */
    @Override
    public Insert names(List<String> colNames) {
        putColNames(wrappedInBrackets(true, colNames));
        return this;
    }

    /**
     * 重载，添加字段名对应的值，list传参
     * @param values
     * @return
     */
    @Override
    public Insert values(List<String> values) {
        putValues(insertEmpty(SQLStringUtils.values, wrappedInBrackets(values)));
        return this;
    }

    /**
     * 生成查询语句并执行
     * @return
     */
    @Override
    public int executeUpdate() {
        System.out.println(buildUpdate());
        return 0;
    }
}
