package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Cinema;
import com.movie.backend.service.CinemaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class CinemaServiceImplTest {
    @Autowired
    CinemaService cinemaService;

    @Test
    @DisplayName("get all the cinemas")
    void getAllCinemas() {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        Assertions.assertNotNull(cinemas);
        Assertions.assertNotEquals(0, cinemas.size());
    }

    @Test
    @DisplayName("get cinema by movie id")
    void getCinemasByMovieId() {
        //movieid存在
        Integer movieId = 1;
        List<Cinema> cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        if (cinemas.size() > 0) {
            Assertions.assertNotNull(cinemas.get(0).getMinPrice());
        }

        //movieid存在，但无相应的影院
        movieId = 2;
        cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());

        //movieid不存在
        movieId = 8;
        cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());

        //movieId非法
        movieId = -2;
        cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());

        //mnovieId为空
        movieId = null;
        cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());
    }
}