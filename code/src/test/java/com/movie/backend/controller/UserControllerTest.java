package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.dto.UserStatus;
import com.movie.backend.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
public class UserControllerTest {
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
    @DisplayName("get a user by id")
    void getUserById() {
        //合法userId
        String url = "http://localhost:" + port + "/getUser/1";
        User user = restTemplate.getForObject(url, User.class);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("customer1", user.getName());

        //合法userId，但user不存在
        url = "http://localhost:" + port + "/getUser/10";
        User user2 = restTemplate.getForObject(url, User.class);
        Assertions.assertNull(user2);

        //不合法userId
        url = "http://localhost:" + port + "/getUser/-1";
        User user3 = restTemplate.getForObject(url, User.class);
        Assertions.assertNull(user3);
    }

    @Test
    @DisplayName("login")
    void login() {
        //正确登录
        String username = "customer1";
        String password = "1234";
        String url = "http://localhost:" + port + "/login/" + username + "/" + password;
        UserStatus userStatus = restTemplate.getForObject(url, UserStatus.class);
        Assertions.assertNotNull(userStatus);
        Assertions.assertEquals(1, userStatus.getUserId());

        //密码错误，用户名正确
        password = "123";
        url = "http://localhost:" + port + "/login/" + username + "/" + password;
        UserStatus userStatus2 = restTemplate.getForObject(url, UserStatus.class);
        Assertions.assertNotNull(userStatus2);
        Assertions.assertEquals(-1, userStatus2.getUserId());

        //无此用户
        username = "cust";
        url = "http://localhost:" + port + "/login/" + username + "/" + password;
        UserStatus userStatus3 = restTemplate.getForObject(url, UserStatus.class);
        Assertions.assertNotNull(userStatus3);
        Assertions.assertEquals(-1, userStatus3.getUserId());
    }

    @Test
    @DisplayName("register a new user")
    void register() {
        //influ3nza注：TODO
    }
}

