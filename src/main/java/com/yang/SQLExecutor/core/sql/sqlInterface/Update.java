package com.yang.SQLExecutor.core.sql.sqlInterface;

import com.yang.SQLExecutor.core.param.WhereParam;

import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-04-21:58
 */
public interface Update extends DML {

    Update update(String tableName);

    Update set(Map<String, Object> valueMap);

    Update set(String... values);

    Update where(WhereParam w);

}
