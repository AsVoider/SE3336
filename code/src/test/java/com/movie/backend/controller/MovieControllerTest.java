package com.movie.backend.controller;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Movie;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        String url = "http://localhost:" + port + "/getMovie?id=1";
        Movie movie = restTemplate.getForObject(url, Movie.class);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(1, movie.getId());

        url = "http://localhost:" + port + "/getMovie?id=-1";
        movie = restTemplate.getForObject(url, Movie.class);
        Assertions.assertNull(movie);
    }

}