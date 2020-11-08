package com.test.yang.TestSQLExecuteor.TestMapper;

import com.yang.SQLExecutor.core.sql.SQL;
import com.yang.SQLExecutor.mapper.GeneralMapper;
import com.yang.SQLExecutor.table.CityTable;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import static com.yang.SQLExecutor.util.SQLStringUtils.*;

/**
 * @auther YF
 * @create 2020-11-02-19:08
 */
public class TestGeneralMapper {
    private GeneralMapper generalMapper;

    @Before
    public void before() {
        generalMapper = new ClassPathXmlApplicationContext("spring/applicationContext.xml")
                .getBean("generalMapper", GeneralMapper.class);
    }

    @Test
    public void testGetCityNames() {
//        System.out.println(generalMapper);

        long st = System.currentTimeMillis();
        List<Map<String, Object>> select = generalMapper.select("select * from city where cityName = 'Beijing'");
        ArrayList<String> list = new ArrayList<>();
        select.forEach(v->{
            list.add(v.get("CountryName").toString());
        });
        List<Map<String, Object>> select1 = generalMapper.select(""/*new SQL().select(null)
                .from(new CityTable())
                .where(w-> w.equal_("cityName", "Beijing"))
                .executeQuery()*/
        );
        select.stream().map(m -> {
            //            System.out.println(select1);
//            System.out.println("m =>" + m);
            select1.stream().filter(m2->Objects.equals(m.get("CountryCode"), m2.get("CountryCode"))).forEach(v->{
                v.forEach(m::put);
            });
            return m;
        }).forEach(System.out::println);
        long eb = System.currentTimeMillis();
//        select.forEach(System.out::println);
        System.out.println((eb - st));


    }

    @Test
    public void testSelect() {
        long st = System.currentTimeMillis();

        List<Map<String, Object>> select = generalMapper.select("select * from city where cityName = 'Beijing'");
        long eb = System.currentTimeMillis();

        select.forEach(System.out::println);
        System.out.println((eb - st));
    }
}
