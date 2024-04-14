package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Session;
import com.movie.backend.service.SessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class SessionServiceImplTest {
    @Autowired
    SessionService sessionService;

    @Test
    @DisplayName("Get Session By Movie And Cinema")
    void getSessionsByMovieAndCinema() {
        var movieId = 1;
        var cinemaId = 1;
        var sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertNotEquals(0, sessions.size());
        Assertions.assertNotNull(sessions.get(0).getSeats());
    }

    @Test
    @DisplayName("Get Session By Invalid Movie And Cinema")
    void getSessionByInvalidMovieAndCinema() {
        var cinemaId = -1;
        var movieId = 1;
        var sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        cinemaId = 1;
        movieId = -1;
        sessions = sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        Integer cinemaId1 = null;
        Integer movieId1 = null;
        sessions = sessionService.getSessionsByMovieAndCinema(movieId1, cinemaId1);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());

        movieId1 = 6;
        sessions = sessionService.getSessionsByMovieAndCinema(movieId1, cinemaId1);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(sessions.size(), 0);
    }

    @Test
    @DisplayName("Get Session By Id")
    void getSessionById() {
        var sessionId = 1;
        var session = sessionService.getSessionById(sessionId);
        Assertions.assertNotNull(session);
        Assertions.assertEquals(session.getMovieId(), 1);
    }

    @Test
    @DisplayName("Get Session By Invalid Id")
    void getSessionByInvalidId() {
        var sessionId = 999;
        var session = sessionService.getSessionById(sessionId);
        Assertions.assertNull(session);

        Integer sessionId1 = null;
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            sessionService.getSessionById(sessionId1);
        });
    }

    @Test
    @DisplayName("Update Session")
    @Transactional
    @Rollback(value = true)
    void updateSession() {
        var sessionId = 1;
        var map = new HashMap<String, String>();
        map.put("id", "1");
        map.put("price", "12.9");
        sessionService.updateSession(map);
        var session = sessionService.getSessionById(sessionId);
        Assertions.assertEquals(session.getPrice(), BigDecimal.valueOf(12.9));
    }

    @Test
    @DisplayName("Update Invalid Session")
    @Transactional
    @Rollback(value = true)
    void updateInvalidSession() {
        var map = new HashMap<String, String>();
        map.put("id", "-123324");
        map.put("sad", "aaa");
        Assertions.assertThrows(NullPointerException.class, () -> {
            sessionService.updateSession(map);
        });
    }
}