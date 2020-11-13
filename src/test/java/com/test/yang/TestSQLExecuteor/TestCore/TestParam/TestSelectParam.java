package com.test.yang.TestSQLExecuteor.TestCore.TestParam;

import com.yang.SQLExecutor.core.param.test.SelectParam;
import com.yang.SQLExecutor.table.TableOperator;
import org.junit.Test;

import java.util.Arrays;

/**
 * @auther YF
 * @create 2020-11-13-20:50
 */
public class TestSelectParam {
    @Test
    public void test01(){
//        SelectParam selectParam = new SelectParam(Arrays.asList("CityName", "CityCode"));
//        selectParam.containAll(TableOperator.city);
    }
    @Test
    public void test02(){
        SelectParam selectParam = new SelectParam(Arrays.asList("CityName", "CountryCode", "CountryName"));
        selectParam.getParamByTable(TableOperator.city).forEach(System.out::println);
        selectParam.getParamByTable(TableOperator.country).forEach(System.out::println);
    }

}
