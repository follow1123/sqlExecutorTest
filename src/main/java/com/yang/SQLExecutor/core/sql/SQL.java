package com.yang.SQLExecutor.core.sql;

import com.yang.SQLExecutor.core.param.SelectParam;
import com.yang.SQLExecutor.core.sql.sqlInterface.Delete;
import com.yang.SQLExecutor.core.sql.sqlInterface.Insert;
import com.yang.SQLExecutor.core.sql.sqlInterface.Select;
import com.yang.SQLExecutor.core.sql.sqlInterface.Update;
import com.yang.SQLExecutor.core.sql.sqlInterface.impl.DeleteImpl;
import com.yang.SQLExecutor.core.sql.sqlInterface.impl.InsertImpl;
import com.yang.SQLExecutor.core.sql.sqlInterface.impl.SelectImpl;
import com.yang.SQLExecutor.core.sql.sqlInterface.impl.UpdateImpl;

import java.util.List;

/**
 * @auther YF
 * @create 2020-11-02-21:00
 */
public class SQL{

    private Select select;

    private Update update;

    private Insert insert;

    private Delete delete;

    public SQL(Select select, Update update, Insert insert, Delete delete) {
        this.select = select;
        this.update = update;
        this.insert = insert;
        this.delete = delete;
    }

    public Select select(SelectParam s) {
        return select.select(s);
    }

    public Insert insert(String tableName) {
        return insert.insert(tableName);
    }

    public Update update(String tableName) {
        return update.update(tableName);
    }

    public Delete delete(String tableName) {
        return delete.delete(tableName);
    }
}
