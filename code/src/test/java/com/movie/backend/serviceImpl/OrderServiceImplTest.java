package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dto.OrderVM;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = BackendApplication.class)
class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("get orders by userId")
    void getOrdersByUserId() {
        //正常参数
        Integer userId = 1;
        List<OrderVM> orderVMS = orderService.getOrdersByUserId(userId);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertNotEquals(0, orderVMS.size());

        //userId非法
        userId = -1;
        orderVMS = orderService.getOrdersByUserId(userId);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertEquals(0, orderVMS.size());

        //userId为null
        userId = null;
        orderVMS = orderService.getOrdersByUserId(userId);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertEquals(0, orderVMS.size());
    }

    @Test
    @DisplayName("get order by orderId")
    void getOrderById() {
        Integer orderId = 15;
        OrderVM orderVM = orderService.getOrderById(orderId);
        Assertions.assertNotNull(orderVM);
        Assertions.assertEquals(orderId, orderVM.getId());

        orderId = -1;
        orderVM = orderService.getOrderById(orderId);
        Assertions.assertNull(orderVM);

        orderId = null;
        orderVM = orderService.getOrderById(orderId);
        Assertions.assertNull(orderVM);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("add order")
    void addOrder() {
        Integer userId = 1;
        Integer sessionId = 1;
        //[1, 1]与[1, 2]
        //未反序列化
        List<Integer> seats = new ArrayList<>();
        seats.add(1);
        seats.add(1);
        seats.add(1);
        seats.add(2);

        Integer orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertNotNull(orderId);
        Assertions.assertEquals(true, orderId > 0);
        OrderVM orderVM = orderService.getOrderById(orderId);

        Assertions.assertNotNull(orderVM);
        BigDecimal price = new BigDecimal("79.00");
        Assertions.assertEquals(price, orderVM.getTotalPrice());

        //测试已被占据的座位
        userId = 1;
        sessionId = 1;
        seats.clear();
        seats.add(2);
        seats.add(5);
        seats.add(2);
        seats.add(6);
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);

        //测试非法座位输入
        userId = 1;
        sessionId = 1;
        seats.clear();
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);

        userId = 1;
        sessionId = 1;
        seats.add(1);
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);

        //测试非法用户
        userId = -1;
        sessionId = 1;
        seats.clear();
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);

        //测试非法场次
        userId = 1;
        sessionId = -1;
        seats.clear();
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);
    }
}