package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.dto.OrderVM;
import com.movie.backend.dto.SeatsDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
class OrderControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("get orders by userId")
    void getOrdersByUserId() {
        Integer userId = 1;
        String url = "http://localhost:" + port + "/getOrders?uid=" + userId;
        List<OrderVM> orderVMS = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertEquals(true, orderVMS.size() > 0);

        //用户id错误
        userId = -1;
        url = "http://localhost:" + port + "/getOrders?uid=" + userId;
        orderVMS = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(orderVMS);
        Assertions.assertEquals(0, orderVMS.size());
    }

    @Test
    @DisplayName("get order by orderId")
    void getOrderById() {
        Integer orderId = 15;
        String url = "http://localhost:" + port + "/getOrder?id=" + orderId;
        OrderVM orderVM = restTemplate.getForObject(url, OrderVM.class);
        Assertions.assertNotNull(orderVM);
        Assertions.assertEquals(orderId, orderVM.getId());

        orderId = -1;
        url = "http://localhost:" + port + "/getOrder?id=" + orderId;
        orderVM = restTemplate.getForObject(url, OrderVM.class);
        Assertions.assertNull(orderVM);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("buy tickets")
    void buyTickets() {
        String url = "http://localhost:" + port + "/buyTickets";
        SeatsDTO seatsDTO = new SeatsDTO();
        seatsDTO.setUserId(1);
        seatsDTO.setSessionId(1);
        List<Integer> seats = new ArrayList<>();
        seats.add(1);
        seats.add(2);
        seatsDTO.setSeats(seats);
        //不会触发rollback
//        Integer orderId = restTemplate.postForObject(url, seatsDTO, Integer.class);
//        Assertions.assertEquals(true, orderId > 0);

        //座位被占
        seats.clear();
        seats.add(2);
        seats.add(5);
        seatsDTO.setSeats(seats);
        Integer orderId1 = restTemplate.postForObject(url, seatsDTO, Integer.class);
        Assertions.assertEquals(true, orderId1 < 0);

        //座位数目错误
        seats.add(2);
        seatsDTO.setSeats(seats);
        Integer orderId2 = restTemplate.postForObject(url, seatsDTO, Integer.class);
        Assertions.assertEquals(true, orderId2 < 0);

        //参数不足
        seats.clear();
        seats.add(1);
        seats.add(2);
        seatsDTO.setSeats(seats);
        seatsDTO.setSessionId(null);
        Integer orderId3 = restTemplate.postForObject(url, seatsDTO, Integer.class);
        Assertions.assertEquals(true, orderId3 < 0);
    }
}