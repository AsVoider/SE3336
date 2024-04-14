package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.dto.CommentDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.Movie;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BackendApplication.class)
class MovieControllerTest {
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
    @DisplayName("get all movies")
    void getAllMovies() {
        String url = "http://localhost:" + port + "/getAllMovies";
        List<Movie> movies = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(movies);
        Assertions.assertNotEquals(0, movies.size());
    }

    @Test
    @DisplayName("get movie by id")
    void getMovie() {
        //movieId存在
        String url = "http://localhost:" + port + "/getMovie/1";
        MovieDTO movie = restTemplate.getForObject(url, MovieDTO.class);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(1, movie.getId());

        //movieId不合法
        url = "http://localhost:" + port + "/getMovie/-1";
        movie = restTemplate.getForObject(url, MovieDTO.class);
        Assertions.assertNull(movie);

        //movieId合法，但不存在
        url = "http://localhost:" + port + "/getMovie/66";
        movie = restTemplate.getForObject(url, MovieDTO.class);
        Assertions.assertNull(movie);
    }

    @Test
    @Transactional
    @DisplayName("update movie")
    void updateMovie() {
        String url = "http://localhost:" + port + "/updateMovie";
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        params.put("title", "流浪地球3");
        restTemplate.put(url, params);

        url = "http://localhost:" + port + "/getMovie/1";
        MovieDTO res1 = restTemplate.getForObject(url, MovieDTO.class);
        Assertions.assertNotNull(res1);
        Assertions.assertEquals("流浪地球3", res1.getTitle());
    }

    @Test
    @Transactional
    @DisplayName("add a comment, and get it by id")
    void addAndGetComment() {
        String url = "http://localhost:" + port + "/comment";
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("movieId", "1");
        params.put("comment", "big bananas");
        restTemplate.put(url, params);

        //合法movieId且存在评论
        url = "http://localhost:" + port + "/getComments/1";
        List<CommentDTO> res1 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res1);
        Assertions.assertEquals(1, res1.size());

        //合法movieId但不存在评论
        url = "http://localhost:" + port + "/getComments/2";
        List<CommentDTO> res2 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res2);
        Assertions.assertEquals(0, res2.size());

        //不合法movieId
        url = "http://localhost:" + port + "/getComments/-2";
        List<CommentDTO> res3 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res3);
        Assertions.assertEquals(0, res3.size());

        //合法movieId但是不存在该电影
        url = "http://localhost:" + port + "/getComments/200";
        List<CommentDTO> res4 = restTemplate.getForObject(url, List.class);
        Assertions.assertNotNull(res4);
        Assertions.assertEquals(0, res4.size());
    }
}