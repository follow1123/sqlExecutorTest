package com.yang.SQLExecutor.core.sql.sqlInterface.impl;

import com.yang.SQLExecutor.core.param.SelectParam;
import com.yang.SQLExecutor.core.param.WhereParam;
import com.yang.SQLExecutor.core.param.operator.SQLOperatorFactory;
import com.yang.SQLExecutor.core.param.operator.WhereOperator;
import com.yang.SQLExecutor.mapper.GeneralMapper;
import com.yang.SQLExecutor.util.SQLStringUtils;

import java.util.*;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-07-13:18
 */
public class BaseSQLImpl {

    private Map<String, Object> sqlMap;

    private List<String> values;


    public BaseSQLImpl() {
        this.sqlMap = new HashMap<>();
        this.values = new ArrayList<>();
    }

    protected void putTableName(String tableName) {
        put(key_tableName, wrappedAsTable(tableName));
    }

    protected void putWhereParam(WhereParam... ws) {
        WhereOperator whereOpe = SQLOperatorFactory.getWhereOpe();
        if (ws.length > 0) {
            values.clear();
            for (WhereParam w : ws) {
                if (!w.getParam(whereOpe).isEmpty()) {
                    values.add(w.getParam(whereOpe));
                }
            }
            put(key_whereParam, insertEmpty(where, connectWithSymbol(and, true, values)));
        }else {
            put(key_whereParam, "");
        }
    }

    protected void putSelectParam(SelectParam s) {
        put(key_selectParam, s.getParam(SQLOperatorFactory.getSelectOpe()));
    }

    protected void putColNames(String colNames) {
        put(key_colNames, colNames);
    }

    protected void putValues(String values) {
        put(key_values, values);
    }

    protected void putSetParam(String setParam) {
        put(key_setParam, setParam);
    }

    protected String buildInsert(){
        return SQLStringUtils.buildInsert(sqlMap);
    }

    protected String buildDelete(){
        return SQLStringUtils.buildDelete(sqlMap);
    }

    protected String buildUpdate(){
        return SQLStringUtils.buildUpdate(sqlMap);
    }

    protected String buildSelect(){
        return SQLStringUtils.buildSelect(sqlMap);
    }
    private void put(String key, String values) {
        sqlMap.put(key, values);
    }
}
