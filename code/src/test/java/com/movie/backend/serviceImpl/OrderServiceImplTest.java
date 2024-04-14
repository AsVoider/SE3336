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
    @DisplayName("Get Orders By UserId")
    void getOrdersByUserId() {
        //正常参数
        Integer userId = 1;
        List<OrderVM> orderVMS = orderService.getOrdersByUserId(userId);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertNotEquals(0, orderVMS.size());
    }

    @Test
    @DisplayName("Get Order By Invalid UserId")
    void getOrderByInvalidUserId() {
        var userId = -1;
        var orderVMS = orderService.getOrdersByUserId(userId);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertEquals(0, orderVMS.size());

        userId = 2;
        orderVMS = orderService.getOrdersByUserId(userId);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertNotEquals(0, orderVMS.size());

        var orderVMS1 = orderService.getOrdersByUserId(null);
        Assertions.assertNotNull(orderVMS1);
        Assertions.assertEquals(0, orderVMS1.size());
    }

    @Test
    @DisplayName("Get Order By OrderId")
    void getOrderById() {
        Integer orderId = 63;
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
    @DisplayName("Add Order")
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
        BigDecimal price = new BigDecimal("229.02");
        Assertions.assertEquals(price, orderVM.getTotalPrice());

        userId = 1;
        sessionId = 1;
        seats.clear();
        seats.add(2);
        seats.add(5);
        seats.add(2);
        seats.add(6);
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);

        userId = 1;
        sessionId = 1;
        seats.clear();
        orderId = orderService.addOrder(userId, sessionId, seats);
        Assertions.assertEquals(-1, orderId);
    }

    @Test
    @DisplayName("Add Invalid Order")
    @Transactional
    @Rollback(value = true)
    void addInvalidOrder() {
        var userId = 1;
        var sessionId = 1;
        List<Integer> seats = new ArrayList<>();
        userId = -1;
        sessionId = 1;
        seats.clear();

        try {
            var orderId = orderService.addOrder(userId, sessionId, seats);
            Assertions.assertEquals(-1, orderId);
        } catch (Exception e) {

        }


        userId = 1;
        sessionId = -1;
        seats.clear();
        try {
            var orderId = orderService.addOrder(userId, sessionId, seats);
            Assertions.assertEquals(-1, orderId);
        } catch (Exception e) {

        }

        Integer userId1 = null;
        Integer sessionId1 = null;
        List<Integer> list1 = null;
        try{
            var orderId = orderService.addOrder(userId1, 1, seats);
            Assertions.assertEquals(-1, orderId);

            orderId = orderService.addOrder(1, sessionId1, seats);
            Assertions.assertEquals(-1, orderId);

            orderId = orderService.addOrder(1, 1, list1);
            Assertions.assertEquals(-1, orderId);
        } catch (Exception e) {

        }

        seats.clear();
        seats.add(1);
        try{
            var orderId = orderService.addOrder(1, 1, seats);
            Assertions.assertEquals(-1, orderId);
        } catch (Exception e) {

        }
    }

    @Test
    @DisplayName("Delete Order")
    @Transactional
    @Rollback(value = true)
    void deleteOrder() {
        var orderId = 3;
        Assertions.assertThrows(Exception.class, () -> {
            orderService.deleteOrder(3);
        });

        orderId = 1;
        orderService.deleteOrder(1);
        assert (true);
    }

    @Test
    @DisplayName("Grab") //废弃的函数 置空
    void grab() {
        orderService.grabTicket(1, 1);
        assert (true);
    }
}