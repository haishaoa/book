package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haishao
 * @create 2020-05-26 23:51
 * @discript :
 */
@WebServlet(name = "CartServlet")
public class CartServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数
        int id = WebUtils.paserInt(req.getParameter("id"),0);

        //2.调用bookService.queryBookById(id):Book得到图书的信息
        Book book = bookService.queryBookById(id);

        //3.把图书信息,转换成为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());

        //4.调用Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        //5.设置最后添加的商品名称
        req.getSession().setAttribute("lastName",cartItem.getName());

        //5.返回购物车总的商品数量和最后一个添加的商品名称
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);

        resp.getWriter().write(resultMapJsonString);
    }

    /**
     * 修改商品数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数 商品编号/商品数量
        int id = WebUtils.paserInt(req.getParameter("id"),0);
        int count = WebUtils.paserInt(req.getParameter("count"),1);

        //2.获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        //3.修改
        if (cart != null){
            cart.updateCount(id,count);

            //4.跳转回原页面
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            //2.清空购物车
            cart.clear();

            //3.重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 删除购物车中某一商品信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取商品编号
        int id = WebUtils.paserInt(req.getParameter("id"),0);

        //2.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart != null){
            //3.删除了购物车商品项
            cart.deleteItem(id);

            //4.重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }


    }

    /**
     * 加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数
        int id = WebUtils.paserInt(req.getParameter("id"),0);

        //2.调用bookService.queryBookById(id):Book得到图书的信息
        Book book = bookService.queryBookById(id);

        //3.把图书信息,转换成为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());

        //4.调用Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        //设置最后添加的商品名称
        req.getSession().setAttribute("lastName",cartItem.getName());

        //5.重定位回 原來 商品列表页面
        resp.sendRedirect(req.getHeader("Referer"));
    }
}



















