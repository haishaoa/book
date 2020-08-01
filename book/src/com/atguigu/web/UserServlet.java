package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author haishao
 * @create 2020-05-17 12:26
 * @discript :
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * ajax请求判断用户名是否可用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistsUsername(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.获取请求的参数
        String username = req.getParameter("username");

        //2.调用userService.existsUsername();
        boolean existsUsername = userService.existsUsername(username);

        //3.把返回的结果封装成为Map对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);

        Gson gson = new Gson();
        //4.将返回结果转为json
        String json = gson.toJson(resultMap);
        //5.返回
        resp.getWriter().write(json);
    }

    /**
     * 处理注销功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
//        1.注销Session中用户登录的信息(或注销Session)
        req.getSession().invalidate();

//        2.重定向到首页(或登录页面)
        resp.sendRedirect(req.getContextPath());
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //2.调用XxxService.xxx()处理业务
        User loginUser = userService.login(new User(null, username, password, null));

        //3.根据login()方法返回结果判断登录是否成功
        if (loginUser != null) {
            //3.1成功

            //保存用户登录之后的信息
            req.getSession().setAttribute("user",loginUser);

            //跳转到成功页面login_success.html
            req.getRequestDispatcher("pages/user/login_success.jsp").forward(req, resp);
        } else {
            //3.2失败

            //把错误信息和回显的表单项信息,保存到request域中
            req.setAttribute("msg", "用户名或密码错误");
            req.setAttribute("username", username);

            //跳转回登录界面
            req.getRequestDispatcher("pages/user/login.jsp").forward(req, resp);
        }
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //2.删除Session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //使用BeanUtils工具类赋值
//        User user = new User();
//        WebUtils.copyParamToBean(req.getParameterMap(),user);
        User user = WebUtils.copyParamToBean(req.getParameterMap(),new User());

        System.out.println(code);
        System.out.println(email);
        System.out.println(password);
        System.out.println(username);

        //2.检查验证码是否正确(暂时写死:abcde)
        if (token != null && token.equalsIgnoreCase(code)) {
            //正确
            //3.检查用户名是否可用
            if (userService.existsUsername(username)) {
                //把回显信息,保存到request域中
                req.setAttribute("msg", "用户名已存在");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                //不可用
                //跳回注册页面
                System.out.println("用户名[" + username + "]已存在!");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                //可用
                //调用service保存到数据库
                userService.registUser(new User(null, username, password, email));

                //跳到注册成功页面regist_success.html
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            //不正确

            //把回显信息,保存到request域中
            req.setAttribute("msg", "验证码错误");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            //跳回注册页面
            System.out.println("验证码错误[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

}



















