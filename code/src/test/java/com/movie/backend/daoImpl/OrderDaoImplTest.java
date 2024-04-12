package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.OrderDao;
import com.movie.backend.entity.Order;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class OrderDaoImplTest {
    @Autowired
    OrderDao orderDao;

    @Test
    @DisplayName("Get User's Orders")
    void getOrdersByUserId() {
        var orders = orderDao.getOrdersByUserId(1);
        Assertions.assertNotNull(orders);
    }

    @Test
    @DisplayName("Get Illegal UserId")
    void getIllegalUserOrder() {
        List<Order> order = null;
        try {
            order = orderDao.getOrdersByUserId(-123);
        } catch (Exception e) {

        }
        Assertions.assertEquals(order.size(), 0);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Order")
    void saveOrder() {
        var order = new Order();
        var isPaid = Byte.MAX_VALUE;
        order.setIsPaid(isPaid);
        var order1 = orderDao.saveOrder(order);
        Assertions.assertNotNull(order1);
        Assertions.assertEquals(order1.getIsPaid(), order.getIsPaid());
        var id = order1.getId();
        var order2 = orderDao.getOrderById(id);
        Assertions.assertEquals(order1.getIsPaid(), order2.getIsPaid());
        Assertions.assertEquals(order1.getIsPaid(), Byte.MAX_VALUE);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Illegal Order")
    void saveIllegalOrder() {
        Order order = null;
        //order.setIsPaid(null);
        Order test = null;
        try {
            test = orderDao.saveOrder(order);
        } catch (Exception e) {

        }
        assertNull(test);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("getOrderById")
    void getOrderById() {
        var saved = new Order();
        saved.setIsPaid(Byte.MAX_VALUE);
        saved.setId(2);
        saved.setTime(new Timestamp(new Date().getTime()));
        saved.setUserId(1);
        saved.setTotalPrice(BigDecimal.valueOf(123.3));
        var order = orderDao.saveOrder(saved);
        var id = order.getId();
        var order1 = orderDao.getOrderById(id);
        System.out.println("order1 is " + order1.getId());
        Assertions.assertEquals(order.getUserId(), 1);
        Assertions.assertEquals(order1.getTotalPrice(), BigDecimal.valueOf(123.3));
    }

    @Test
    @DisplayName("Get Illegal Id")
    void GetIllegalId() {
        Order order = null;
        try {
            order = orderDao.getOrderById(12345);
        } catch (Exception e) {

        }

        Assertions.assertNull(order);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Test Delete")
    void TestDelete() {
        var orders = orderDao.getOrdersByUserId(1);
        assert (!orders.isEmpty());
        var deleteId = orders.get(0).getId();
        orderDao.deleteOrder(deleteId);
        orders = orderDao.getOrdersByUserId(1);
        assert (orders.isEmpty() || orders.get(0).getId() != deleteId);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Test Wrong Delete")
    void TestDeleteWrong() {
        try {
            orderDao.deleteOrder(-123);
        } catch (Exception e) {
            assert (true);
        }
    }
}