package com.yang.SQLExecutor.core.sql.sqlInterface;

import com.yang.SQLExecutor.core.param.WhereParam;

/**
 * @auther YF
 * @create 2020-11-04-22:01
 */
public interface Delete extends DML {
    Delete delete(String tableName);

    Delete where(WhereParam w);
}
