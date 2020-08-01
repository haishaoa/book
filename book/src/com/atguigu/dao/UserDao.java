package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * @author haishao
 * @create 2020-04-22 16:20
 * @discript :
 */
public interface UserDao {



    /**
     * 根据用户名查询用户信息(用户注册验证)
     * @param username
     * @return  如果返回null,说明没有这个用户,反之亦然
     */
    public User queryUserByUsername(String username);

    /**
     * 保存用户信息(用户注册保存)
     * @param user
     * @return  返回-1表示操作失败，其他是sql语句影响的行数
     */
    public int saveUser(User user);

    /**
     * 根据用户名和密码查询用户信息(用户登录验证)
     * @param username
     * @param password
     * @return  如果返回null,说明用户名或密码错误
     */
    public User queryUserByUsernameAndPassword(String username, String password);
}


















