package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Cinema;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.RestClientException;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
class CinemaControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("get all cinemas")
    void getAllCinemas() {
        String url = "http://localhost:" + port + "/getAllCinemas";
        List<Cinema> res = restTemplate.getForObject(url, List.class);
        Assertions.assertEquals(3, res.size());
    }

    @Test
    @DisplayName("get cinemas by movieId")
    void getCinemasByMovieId() {
        //movieId存在
        String url = "http://localhost:" + port + "/getCinemas/1";
        Map<String, String> params = new HashMap<>();
        List<Cinema> res1 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res1);
        Assertions.assertEquals(true, res1.size() > 0);

        //movieId存在，但无对应影院
        url = "http://localhost:" + port + "/getCinemas/2";
        List<Cinema> res2 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res2);
        Assertions.assertEquals(0, res2.size());

        //movieId合法，但不存在
        url = "http://localhost:" + port + "/getCinemas/100";
        List<Cinema> res3 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res3);
        Assertions.assertEquals(0, res3.size());

        //movieId不合法
        url = "http://localhost:" + port + "/getCinemas/-1";
        List<Cinema> res4 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res4);
        Assertions.assertEquals(0, res4.size());

        //movieId为空
        //influ3nza注：PathVariable不能为null。即使添加request = false，它依然会报错。
        //见下方测试。
    }

    @Test
    @DisplayName("get cinema by its id")
    void getCinemaById() {
        //id存在
        String url = "http://localhost:" + port + "/getCinema/1";
        Cinema res1 = restTemplate.getForObject(url, Cinema.class);
        Assertions.assertNotNull(res1);
        Assertions.assertEquals(1, res1.getId());

        //id合法，但是不存在
        url = "http://localhost:" + port + "/getCinema/20";
        Cinema res2 = restTemplate.getForObject(url, Cinema.class);
        Assertions.assertNull(res2);

        //id不合法
        url = "http://localhost:" + port + "/getCinema/-20";
        Cinema res3 = restTemplate.getForObject(url, Cinema.class);
        Assertions.assertNull(res3);

        //id为空
        //同上。
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("update a cinema")
    void updateCinema() {
        String url = "http://localhost:" + port + "/updateCinema";
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        //influ3nza注：不支持修改除了name和address以外的信息
//        params.put("distance", "114514");
        params.put("name", "big banana");
        params.put("address", "SJTU");
        restTemplate.put(url, params);

        url = "http://localhost:" + port + "/getCinema/1";
        Cinema res1 = restTemplate.getForObject(url, Cinema.class);
        Assertions.assertNotNull(res1);
        Assertions.assertEquals("big banana", res1.getName());
        Assertions.assertEquals("SJTU", res1.getAddress());
    }

    @Test
    @DisplayName("missing params exception")
    void exceptionTest() {
        RestClientException exception = Assertions.assertThrows(
                RestClientException.class, ()->{
                    String url = "http://localhost:" + port + "/getCinemas";
                    System.out.println(url);
                    List<Cinema> res4 = restTemplate.getForObject(url, List.class);
                }
        );
    }
}