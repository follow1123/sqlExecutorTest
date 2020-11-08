package com.test.yang.TestSQLExecuteor.TestCore.TestSQLExecutor;

import com.yang.SQLExecutor.core.param.operator.SelectOperator;
import com.yang.SQLExecutor.core.param.operator.WhereOperator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @auther YF
 * @create 2020-11-03-19:02
 */
public class TestSQLExecutor {

//    private SQLExecutor sqlExecutor;
//
//    @Before
//    public void before() {
//        sqlExecutor = new ClassPathXmlApplicationContext("spring/applicationContext.xml")
//                .getBean("sqlExecutor", SQLExecutor.class);
//    }
//
//    @Test
//    public void test01() {
//        /*
//         * Belgrade
//         * Brasília
//         * Athens
//         * Paris
//         * */
//        List<Object> select = sqlExecutor.select("city",
//                s -> s.namesAsLabels("cityName","cn", "cityCode", "cc"),
//                w -> w.in("cityName", "Beijing", "Belgrade", "Brasília")
//                        .not_("cityCode", "PEK"),
//                null
//        );
//        select.forEach(System.out::println);
//    }
//
//    @Test
//    public void test02(){
//        WhereOperator w = new WhereOperator();
//    }
//    @Test
//    public void test03(){
//        SelectOperator s = new SelectOperator();
//
//        System.out.println(s.names("a", "b", "c").labels("A", "B", "C"));
//        System.out.println(s.namesAsLabels("a", "A", "b", "B", "c", "C"));
//    }
}
