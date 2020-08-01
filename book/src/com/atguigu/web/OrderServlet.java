package com.atguigu.web;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.atguigu.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author haishao
 * @create 2020-05-28 23:16
 * @discript :
 */
@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseServlet {
    OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        //2.获取UserId
        User loginUser = (User) req.getSession().getAttribute("user");

        //如果用户没登录,跳转到登录页面
        if (loginUser == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }

        Integer userId = loginUser.getId();

        //3.调用orderService.createOrder(Cart,userId);生成订单
        String orderId = orderService.createOrder(cart, userId);

//        req.setAttribute("orderId",orderId);
        req.getSession().setAttribute("orderId",orderId);

        //4.请求转发到/pages/cart/checkout.jsp-->改为重定向
        resp.sendRedirect("/book/pages/cart/checkout.jsp");
    }
}




















