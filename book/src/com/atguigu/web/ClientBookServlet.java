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

/**
 * @author haishao
 * @create 2020-05-25 18:10
 * @discript :
 */
@WebServlet(name = "ClientBookServlet")
public class ClientBookServlet extends BaseServlet {
    //创建BookService对象
    private BookService bookService = new BookServiceImpl();

    /**
     * 处理价格区间查询功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //1.获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.paserInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.paserInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.paserInt(req.getParameter("min"),0);
        int max = WebUtils.paserInt(req.getParameter("max"),Integer.MAX_VALUE);

        //2.调用BookService.page(pageNo,pageSize):Page对象
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");

//        如果有最小价格的参数,追加到分页条的地址参数中
        if (req.getParameter("min") != null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        //        如果有最大价格的参数,追加到分页条的地址参数中
        if (req.getParameter("max") != null){
            sb.append("&max=").append(req.getParameter("max"));
        }

        page.setUrl(sb.toString());

        //3.保存Page对象到Request域中
        req.setAttribute("page",page);

        //4.请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

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

        page.setUrl("client/bookServlet?action=page");

        //3.保存Page对象到Request域中
        req.setAttribute("page",page);

        //4.请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
}
