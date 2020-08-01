package com.atguigu.service;

import com.atguigu.pojo.Cart;

/**
 * @author haishao
 * @create 2020-05-28 22:54
 * @discript :
 */
public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
}
