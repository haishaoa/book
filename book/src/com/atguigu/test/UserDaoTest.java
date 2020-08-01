package com.atguigu.test;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import org.junit.Test;


/**
 * @author haishao
 * @create 2020-04-22 22:12
 * @discript :
 */
public class UserDaoTest {

    UserDao userDao = new UserDaoImpl();;

    @Test
    public void queryUserByUsername() {
        System.out.println(userDao.queryUserByUsername("admin"));
    }

    @Test
    public void saveUser() {
        System.out.println(userDao.saveUser(new User(null,"admink222","123456","waa515@qq.com")));
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        System.out.println(userDao.queryUserByUsernameAndPassword("admin", "admin"));
    }
}