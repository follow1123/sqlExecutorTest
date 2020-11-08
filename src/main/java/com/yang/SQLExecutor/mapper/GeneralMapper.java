package com.yang.SQLExecutor.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-02-21:01
 */
public interface GeneralMapper {
    List<Map<String, Object>> select(@Param("sql") String sql);
}
