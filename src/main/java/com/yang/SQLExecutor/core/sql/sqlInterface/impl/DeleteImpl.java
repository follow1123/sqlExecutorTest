package com.yang.SQLExecutor.core.sql.sqlInterface.impl;

import com.yang.SQLExecutor.core.param.WhereParam;
import com.yang.SQLExecutor.core.param.operator.SQLOperatorFactory;
import com.yang.SQLExecutor.core.sql.sqlInterface.Delete;
import com.yang.SQLExecutor.mapper.GeneralMapper;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-05-20:12
 */
public class DeleteImpl extends BaseSQLImpl implements Delete {

    private GeneralMapper generalMapper;

    public DeleteImpl(GeneralMapper generalMapper) {
        this.generalMapper = generalMapper;
    }

    public DeleteImpl() {
        super();
    }

    /**
     * 添加表名
     * @param tableName
     * @return
     */
    @Override
    public Delete delete(String tableName) {
        putTableName(tableName);
        return this;
    }

    @Override
    public Delete where(WhereParam w) {
        putWhereParam(w);
        return this;
    }

    @Override
    public int executeUpdate() {
        System.out.println(buildDelete());
        return 0;
    }
}
