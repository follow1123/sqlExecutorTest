package com.yang.SQLExecutor.core.sql.sqlInterface;

import java.util.List;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-05-20:07
 */
public interface DQL {

    <T> List<T> executeQuery(Class<T> tClass);

    List<Map<String, Object>> executeQuery();


}
