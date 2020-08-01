package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author haishao
 * @create 2020-04-23 9:11
 * @discript :
 */
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //2.调用XxxService.xxx()处理业务
        User loginUser = userService.login(new User(null, username, password, null));

        //3.根据login()方法返回结果判断登录是否成功
        if (loginUser != null){
            //3.1成功
            //跳转到成功页面login_success.html
            req.getRequestDispatcher("pages/user/login_success.jsp").forward(req,resp);
        }else {
            //3.2失败

            //把错误信息和回显的表单项信息,保存到request域中
            req.setAttribute("msg","用户名或密码错误");
            req.setAttribute("username",username);

            //跳转回登录界面
            req.getRequestDispatcher("pages/user/login.jsp").forward(req,resp);
        }
    }
}
