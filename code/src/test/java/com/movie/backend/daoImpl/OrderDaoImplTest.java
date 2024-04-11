package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.OrderDao;
import com.movie.backend.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class OrderDaoImplTest {
    @Autowired
    OrderDao orderDao;

    @Test
    @DisplayName("get user's orders")
    void getOrdersByUserId() {
        List<Order> orders = orderDao.getOrdersByUserId(1);
        Assertions.assertNotNull(orders);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save the order and get order by id")
    void saveOrder() {
        Order order = new Order();
        Byte isPaid = Byte.MAX_VALUE;
        order.setIsPaid(isPaid);
        Order order1 = orderDao.saveOrder(order);
        Assertions.assertNotNull(order1);
        Assertions.assertEquals(order1.getIsPaid(), order.getIsPaid());
        Integer id = order1.getId();
        Order order2 = orderDao.getOrderById(id);
        Assertions.assertEquals(order1.getIsPaid(), order2.getIsPaid());
    }

    @Test
    @DisplayName("getOrderById")
    void getOrderById() {
        //Test above
    }
}