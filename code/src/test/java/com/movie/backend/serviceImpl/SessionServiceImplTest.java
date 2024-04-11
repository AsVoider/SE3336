package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Session;
import com.movie.backend.service.SessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class SessionServiceImplTest {
    @Autowired
    SessionService sessionService;

    @Test
    @DisplayName("get session by movie and cinema")
    void getSessionsByMovieAndCinema() {
        //正常参数，应该解析session的seats
        Integer movieId = 1;
        Integer cinemaId = 1;
        List<Session> sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertNotEquals(0, sessions.size());
        Assertions.assertNotNull(sessions.get(0).getSeats());

        //cinema不存在
        cinemaId = -1;
        movieId = 1;
        sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        //movie不存在
        cinemaId = 1;
        movieId = -1;
        sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        //传入非法参数
        cinemaId = null;
        movieId = null;
        sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());
    }
}