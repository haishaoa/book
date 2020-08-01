package com.atguigu.test;


import com.atguigu.utils.JDBCUtils;
import org.junit.Test;

/**
 * @author haishao
 * @create ${Year}-04-22 12:35
 * @discript :
 */
public class JdbcUtilsTest {
    @Test
    public void JdbcUtilsTest(){
        System.out.println(JDBCUtils.getConnection());
    }
}
