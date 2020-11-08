package com.yang.SQLExecutor.core.param;

import com.yang.SQLExecutor.core.param.operator.SelectOperator;

/**
 * @auther YF
 * @create 2020-11-03-19:12
 */
public interface SelectParam {
    String getParam(SelectOperator s);
}
