package com.movie.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.movie.backend.BackendApplication;
import com.movie.backend.dto.MovieDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
public class RecommendControllerTest {
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
    @DisplayName("get rate")
    void getRate() {
        //存在该rate
        String url = "http://localhost:" + port + "/getRate";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("userId", 1);
        paramMap.add("movieId", 1);
        JSONObject response = restTemplate.postForObject(url, paramMap, JSONObject.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(5.0, response.get("rate"));

        //不存在该rate
        MultiValueMap<String, Object> paramMap1 = new LinkedMultiValueMap<>();
        paramMap1.add("userId", 11);
        paramMap1.add("movieId", 1);
        JSONObject response1 = restTemplate.postForObject(url, paramMap1, JSONObject.class);
        Assertions.assertNotNull(response1);
        Assertions.assertEquals(0.0, response1.get("rate"));
    }

    @Test
    @DisplayName("get recommend")
    void getRecommend() {
        String url = "http://localhost:" + port + "/recommend/1";
        List<MovieDTO> res = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(true, res.size() > 0);
    }

    @Test
    @DisplayName("add rate")
    void rate() {
        //influ3nza注：TODO
    }
}

