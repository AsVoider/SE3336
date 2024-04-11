package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Cinema;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
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
        String url = "http://localhost:" + port + "/getCinemas?movieId=1";
        Map<String, String> params = new HashMap<>();
        List<Cinema> res1 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res1);
        Assertions.assertEquals(true, res1.size() > 0);

        url = "http://localhost:" + port + "/getCinemas?movieId=100";
        List<Cinema> res2 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res2);
        Assertions.assertEquals(0, res2.size());

        url = "http://localhost:" + port + "/getCinemas?movieId=-1";
        List<Cinema> res3 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res3);
        Assertions.assertEquals(0, res3.size());

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