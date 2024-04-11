package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
class SessionControllerTest {
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
    @DisplayName("get sessions by movie and cinema")
    void getSessions() {
        Integer movieId = 1;
        Integer cinemaId = 1;
        String url = "http://localhost:" + port + "/getSessions?movieId="
                + movieId + "&cinemaId=" + cinemaId;
        List<Session> sessions = restTemplate.getForObject(url, List.class);
        //反序列化
        List<Session> sessions1 = new ArrayList<>();
        for (int i = 0; i < sessions.size(); ++i) {
            String jsonStr = JSON.toJSONString(sessions.get(i));
            Session session = JSON.parseObject(jsonStr, Session.class);
            sessions1.add(session);
        }
        Assertions.assertNotNull(sessions1);
        Assertions.assertNotEquals(0, sessions1.size());
        Assertions.assertNotNull(sessions1.get(0).getSeats());

        movieId = -1;
        cinemaId = 1;
        url = "http://localhost:" + port + "/getSessions?movieId="
                + movieId + "&cinemaId=" + cinemaId;
        sessions = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        movieId = 1;
        cinemaId = -1;
        url = "http://localhost:" + port + "/getSessions?movieId="
                + movieId + "&cinemaId=" + cinemaId;
        sessions = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

    }
}