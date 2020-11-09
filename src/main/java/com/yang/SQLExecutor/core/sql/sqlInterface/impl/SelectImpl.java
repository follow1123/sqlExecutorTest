package com.yang.SQLExecutor.core.sql.sqlInterface.impl;

import com.yang.SQLExecutor.core.param.SelectParam;
import com.yang.SQLExecutor.core.param.WhereParam;
import com.yang.SQLExecutor.core.sql.sqlInterface.Select;
import com.yang.SQLExecutor.mapper.GeneralMapper;
import com.yang.SQLExecutor.table.Table;

import java.util.List;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-05-20:11
 */
public class SelectImpl extends SimpleSelect {


    public SelectImpl(GeneralMapper generalMapper) {
        super(generalMapper);
    }

    public SelectImpl() {
    }

    @Override
    public Select select(SelectParam s) {
        return super.select(s);
    }

    @Override
    public Select where(WhereParam... w) {
        return super.where(w);
    }

    @Override
    public Select from(Table table) {
        return super.from(table);
    }

    @Override
    public Select joinOn(Table table, String key1, String key2) {
        return this;
    }

    @Override
    public <T> List<T> executeQuery(Class<T> tClass) {
        return super.executeQuery(tClass);
    }


    @Override
    public List<Map<String, Object>> executeQuery() {
        return super.executeQuery();
    }
}
