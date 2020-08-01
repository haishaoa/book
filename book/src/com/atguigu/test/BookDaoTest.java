package com.atguigu.test;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author haishao
 * @create 2020-05-17 19:22
 * @discript :
 */
public class BookDaoTest {
    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems() {
        for (Book book : bookDao.queryForPageItems(0, Page.PAGE_SIZE)) {
            System.out.println(book);
        }
    }

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"国哥为什么这么帅！","191125",new BigDecimal(9999),100,0,null));
    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(2);
    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(1,"大家都可以","国哥",new BigDecimal(999),10000,0,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(1));
    }

    @Test
    public void queryBooks() {
        for (Book queryBook :bookDao.queryBooks()){
            System.out.println(queryBook);
        }
    }

    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10, 1000));
    }

    @Test
    public void queryForPageItemsByPrice() {
        for (Book book : bookDao.queryForPageItemsByPrice(0,4,10,100)){
            System.out.println(book);
        }
    }
}












