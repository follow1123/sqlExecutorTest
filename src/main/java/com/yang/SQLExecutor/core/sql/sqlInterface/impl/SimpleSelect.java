package com.yang.SQLExecutor.core.sql.sqlInterface.impl;

import com.yang.SQLExecutor.core.param.SelectParam;
import com.yang.SQLExecutor.core.param.WhereParam;
import com.yang.SQLExecutor.core.param.operator.SQLOperatorFactory;
import com.yang.SQLExecutor.core.sql.sqlInterface.Select;
import com.yang.SQLExecutor.mapper.GeneralMapper;
import com.yang.SQLExecutor.table.Table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-07-12:44
 */
public abstract class SimpleSelect extends BaseSQLImpl implements Select {

    private GeneralMapper generalMapper;

    public SimpleSelect(GeneralMapper generalMapper) {
        this.generalMapper = generalMapper;
    }

    public SimpleSelect() {
        super();
    }

    @Override
    public Select select(SelectParam s) {
        putSelectParam(s);
        return this;
    }

    @Override
    public Select where(WhereParam... ws) {
        putWhereParam(ws);
        return this;
    }

    @Override
    public Select from(Table table) {
        putTableName(table.toString());
        return this;
    }

    @Override
    public <T> List<T> executeQuery(Class<T> tClass) {
        return parse(tClass, executeQuery());
    }

    /**
     * 将一个字符串转换为驼峰命名方式
     * @param name
     * @return
     */
    public String headToLower(String name){
        return String.valueOf(name.charAt(0)).toLowerCase().concat(name.substring(1));
    }

    /**
     * 将一个集合里面的map全部转换为对应的对象
     * @param tClass
     * @param values
     * @param <T>
     * @return
     */
    private <T> List<T> parse(Class<T> tClass, List<Map<String, Object>> values) {
        ArrayList<T> ts = new ArrayList<>();
        values.forEach(v -> {
            try {
                ts.add(mapToObject(tClass, v));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return ts;
    }

    /**
     * 将map转换为对应的对象
     * @param cl
     * @param map
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> T mapToObject(Class<T> cl, Map<String, Object> map) throws Exception{
        T t = cl.getConstructor().newInstance();
        for (String k : map.keySet()) {
            try {
                Field field = cl.getDeclaredField(headToLower(k));
                field.setAccessible(true);
                field.set(t, map.get(k));
                field.setAccessible(false);
            }catch (NoSuchFieldException e){
                System.err.println(e.getMessage().concat(" 不存在该属性"));
                break;
            }
        }
        return t;
    }
    @Override
    public List<Map<String, Object>> executeQuery() {
        return generalMapper.select(buildSelect());
    }
}
