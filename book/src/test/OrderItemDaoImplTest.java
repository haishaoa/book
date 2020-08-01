package test;

import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author haishao
 * @create 2020-05-28 22:50
 * @discript :
 */
public class OrderItemDaoImplTest {

    @Test
    public void saveOrderItem() {
        OrderItemDao orderItemDao = new OrderItemDaoImpl();

        orderItemDao.saveOrderItem(new OrderItem(null,"java",1,new BigDecimal(100),new BigDecimal(100),"12"));
        orderItemDao.saveOrderItem(new OrderItem(null,"css",2,new BigDecimal(100),new BigDecimal(200),"12"));
        orderItemDao.saveOrderItem(new OrderItem(null,"html",1,new BigDecimal(10),new BigDecimal(10),"12"));
        orderItemDao.saveOrderItem(new OrderItem(null,"js",1,new BigDecimal(100),new BigDecimal(100),"12"));
    }
}