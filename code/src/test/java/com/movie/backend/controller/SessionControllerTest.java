package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.alibaba.fastjson.JSON;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String url = "http://localhost:" + port + "/getSessions/"
                + movieId + "/" + cinemaId;
        List<Session> sessions = restTemplate.getForObject(url, List.class);

        Assertions.assertNotNull(sessions);
        Assertions.assertNotEquals(0, sessions.size());

        //非法movieId
        movieId = -1;
        cinemaId = 1;
        url = "http://localhost:" + port + "/getSessions/"
                + movieId + "/" + cinemaId;
        sessions = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        //非法cinemaId
        movieId = 1;
        cinemaId = -1;
        url = "http://localhost:" + port + "/getSessions/"
                + movieId + "/" + cinemaId;
        sessions = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());
    }

    @Test
    @DisplayName("get all sessions")
    void getAllSessions() {
        String url = "http://localhost:" + port + "/getAllSessions";
        List<Session> sessions = restTemplate.getForObject(url, List.class);

        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(true, sessions.size() > 0);
    }

    @Test
    @DisplayName("get a session by id")
    void getSessionById() {
        //合法Id
        String url = "http://localhost:" + port + "/getSession/1";
        Session session = restTemplate.getForObject(url, Session.class);
        Assertions.assertNotNull(session);
        Assertions.assertEquals(1, session.getId());

        //不合法Id
        url = "http://localhost:" + port + "/getSession/-100";
        Session session2 = restTemplate.getForObject(url, Session.class);
        Assertions.assertNull(session2);

        //合法Id但是session不存在
        url = "http://localhost:" + port + "/getSession/100";
        Session session3 = restTemplate.getForObject(url, Session.class);
        Assertions.assertNull(session3);
    }

    @Test
    @DisplayName("update a session")
    void updateSession() {
        String url = "http://localhost:" + port + "/updateSession";
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        params.put("price", "114.51");
        restTemplate.put(url, params);

        url = "http://localhost:" + port + "/getSession/1";
        Session session = restTemplate.getForObject(url, Session.class);
        Assertions.assertNotNull(session);
        // influ3nza notes that this needs modification.
         BigDecimal b = BigDecimal.valueOf(114.51);
         Assertions.assertEquals(b, session.getPrice());

        url = "http://localhost:" + port + "/updateSession";
        Map<String, String> params1 = new HashMap<>();
        params.put("id", "1");
        params.put("price", "39.50");
        restTemplate.put(url, params1);
    }
}
