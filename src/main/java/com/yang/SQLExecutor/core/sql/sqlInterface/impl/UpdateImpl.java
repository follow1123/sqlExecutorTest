package com.yang.SQLExecutor.core.sql.sqlInterface.impl;

import com.yang.SQLExecutor.core.param.WhereParam;
import com.yang.SQLExecutor.core.param.operator.SQLOperatorFactory;
import com.yang.SQLExecutor.core.param.operator.WhereOperator;
import com.yang.SQLExecutor.core.sql.sqlInterface.Update;
import com.yang.SQLExecutor.mapper.GeneralMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-05-20:12
 */
public class UpdateImpl extends BaseSQLImpl implements Update {

    private GeneralMapper generalMapper;

    public UpdateImpl(GeneralMapper generalMapper) {
        this.generalMapper = generalMapper;
    }

    public UpdateImpl() {
        super();
    }

    /**
     * 添加表名
     * @param tableName
     * @return
     */
    @Override
    public Update update(String tableName) {
        putTableName(tableName);
        return this;
    }

    /**
     * 添加set后面的值
     * @param valueMap
     * @return
     */
    @Override
    public Update set(Map<String, Object> valueMap) {
        List<String> collect = valueMap.keySet().stream()
                .map(k -> insertEqual(k, valueMap.get(k)))
                .collect(Collectors.toList());
        putSetParam(connectWithComma(true, collect));
        return this;
    }


    /**
     * 重载，添加set后面的值，可变成参数
     * @param values
     * @return
     */
    @Override
    public Update set(String... values) {
        HashMap<String, Object> vMap = new HashMap<>();
        Stream.iterate(0, t -> t + 2)
                .limit(values.length - 2)
                .forEach(i -> vMap.put(values[i], values[i + 1]));
        set(vMap);
        return this;
    }


    /**
     * 添加where后面的参数
     * @param w
     * @return
     */
    @Override
    public Update where(WhereParam w) {
        putWhereParam(w);
        return this;
    }

    /**
     * 生成查询语句并执行
     * @return
     */
    @Override
    public int executeUpdate() {
        buildUpdate();
        return 0;
    }
}
