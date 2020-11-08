package com.yang.SQLExecutor.core.param;

import com.yang.SQLExecutor.core.param.operator.WhereOperator;

/**
 * @auther YF
 * @create 2020-11-03-19:55
 */
public interface WhereParam {
    String getParam(WhereOperator o);
}
