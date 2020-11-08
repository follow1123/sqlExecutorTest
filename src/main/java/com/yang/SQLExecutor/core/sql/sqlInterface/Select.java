package com.yang.SQLExecutor.core.sql.sqlInterface;

import com.yang.SQLExecutor.core.param.SelectParam;
import com.yang.SQLExecutor.core.param.WhereParam;
import com.yang.SQLExecutor.table.Table;

/**
 * @auther YF
 * @create 2020-11-04-21:57
 */
public interface Select extends DQL{

    Select select(SelectParam s);

    Select where(WhereParam... ws);

    Select from(Table table);

    Select joinOn(Table table, String key1, String key2);

//    Select order(OrderByParam o);
}
