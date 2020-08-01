package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author haishao
 * @create 2020-05-17 19:46
 * @discript :
 */
@WebServlet(name = "BookServlet")
public class BookServlet extends BaseServlet {
    //创建BookService对象
    private BookService bookService = new BookServiceImpl();

    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.paserInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.paserInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //2.调用BookService.page(pageNo,pageSize):Page对象
        Page<Book> page = bookService.page(pageNo,pageSize);

        page.setUrl("manager/bookServlet?action=page");

        //3.保存Page对象到Request域中
        req.setAttribute("page",page);

        //4.请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    protected void getBook(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.获取请求的参数图书编号
        int id = WebUtils.paserInt(req.getParameter("id"),0);

        //2.调用bookService.queryBookById(id);
        Book book = bookService.queryBookById(id);

        //3.保存图书到Request域中
        req.setAttribute("book",book);

        //4.请求转发到pages/manager/book_edit.jsp页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }

    protected void add(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //将传递的pageNo加1,使其保持在最后一页
        int pageNo = WebUtils.paserInt(req.getParameter("pageNo"),0);
        pageNo += 1;

        //1.获取请求的参数==封装成为Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());

        //2.调用BookService.addBook()保存图书
        bookService.addBook(book);

        //3.跳到图书列表页面
//        (当用户按下F5,会发生表单重复提交)req.getRequestDispatcher("/manager/bookServlet?action=list").forward(req,resp);

        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    protected void delete(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.获取请求的参数id,图书编程
        int id = WebUtils.paserInt(req.getParameter("id"),0);

        //2.调用bookService.deleteBookById();删除图书
        bookService.deleteBookById(id);

        //3.重定向回图书管理页面-->/book/manager/bookServlet?action=list
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.获取请求的参数==封装成为Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());

        //2.调用BookService.updateBook(book);修改对象
        bookService.updateBook(book);

        //3.重定位回图书列表管理页面
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void list(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();

        //2.把全部图书保存到Request域中
        req.setAttribute("books",books);

        //3.请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
//        req.getRequestDispatcher("/pages/common/login_sucess_menu.jsp").forward(req,resp);
//        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
}
















