package com.yang.SQLExecutor.core.sql.sqlInterface;

import com.yang.SQLExecutor.mapper.GeneralMapper;

import java.util.List;

/**
 * @auther YF
 * @create 2020-11-04-22:00
 */
public interface Insert extends DML {

    Insert insert(String tableName);

    Insert names(String... colNames);

    Insert values(String... values);

    Insert names(List<String> colNames);

    Insert values(List<String> values);
}
