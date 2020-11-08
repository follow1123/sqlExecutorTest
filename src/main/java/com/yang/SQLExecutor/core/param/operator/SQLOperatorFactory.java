package com.yang.SQLExecutor.core.param.operator;

/**
 * @auther YF
 * @create 2020-11-06-19:53
 */
public class SQLOperatorFactory {


    public static SelectOperator getSelectOpe(){
        return SelectOperator.getInstance();
    }

    public static WhereOperator getWhereOpe(){
        return WhereOperator.getInstance();
    }


}
