package com.test.yang;

import com.yang.util.PojoUtils;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @auther YF
 * @create 2020-11-02-18:44
 */
public class InitPojo {
    @Test
    public void testInit(){
        DefaultSqlSessionFactory sqlSessionFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml").getBean("sqlSessionFactory", DefaultSqlSessionFactory.class);
        PojoUtils.initPojo(sqlSessionFactory.openSession().getConnection());
    }
}
