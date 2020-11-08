package com.test.yang.TestSQLExecuteor.TestCore.TestSQL;

import com.yang.SQLExecutor.core.sql.SQL;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static com.yang.SQLExecutor.util.TimeTest.*;
import static com.yang.SQLExecutor.table.TableOperator.*;
import static com.yang.SQLExecutor.core.param.operator.SQLOperator.*;

/**
 * @auther YF
 * @create 2020-11-02-21:31
 */
public class TestSQL {


    private ApplicationContext applicationContext;

    private SQL sql;

    @Before
    public void before() {
        applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        sql = applicationContext.getBean("sql", SQL.class);
    }

    @Test
    public void test() {
//        SQL sql = new SQL(new CityTable());
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("CityName", "Beijing");
//        sql.select(()-> Arrays.asList(CityTable.CityName,CityTable.CityCode))
//        .where(sql.Equal(param));
//        System.out.println(sql);

//        sql.insert()
    }

    @Test
    public void test01() {
        SqlSessionTemplate sqlSession = applicationContext.getBean("sqlSession", SqlSessionTemplate.class);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("colNames", Arrays.asList("CityName", "CityCode"));
        List<Object> select = sqlSession.selectList("select", paramMap);
    }

    @Test
    public void test02() {

        sql.delete("city")
//                .where(w -> w.equal_("cityName", "Beijing"))
                .executeUpdate();
//        sql.insert().names().values().executeUpdate()
    }

    @Test
    public void test03() {
        sql.insert("city")
                .names("cityName", "cityCode", "countryCode")
                .values("123", "12321", "21321")
                .executeUpdate();

    }

    @Test
    public void test04() {
        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put("cityName", "Beijing");
        valueMap.put("CityCode", "PEK");
        sql.update("city")
//                .set("cityName", "Beijing", "CityCode", "PEK")
//                .set(valueMap)
//                .where(w -> w.operator(equal,"countryCode", "123"))
                .executeUpdate();
    }

    @Test
    public void test05() {
        Stream.iterate(0, t -> t + 2).limit(100).forEach(System.out::println);

    }

    @Test
    public void test06() {
        testTime(() ->
                sql.select(all)
                        .from(city)
                        .where(equal(city.cityName, "  213")
                                , in(city.cityCode, "d12", "12321"))
                        .executeQuery().forEach(System.out::println));

    }

    @Test
    public void test07() {

    }

}
