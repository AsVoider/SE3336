package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.SessionDao;
import com.movie.backend.entity.Movie;
import com.movie.backend.entity.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class SessionDaoImplTest {
    @Autowired
    SessionDao sessionDao;

    @Test
    @DisplayName("Get Session And Movie")
    void getSessionByMovieAndRoom() {
        Integer movieId = 1;
        Integer roomId = 2;
        List<Session> sessions = sessionDao.getSessionByMovieAndRoom(movieId, roomId);
        Assertions.assertNotNull(sessions);

        if (sessions.size() > 0) {
            Assertions.assertEquals(sessions.get(0).getRoomId(), roomId);
            Assertions.assertEquals(sessions.get(0).getMovieId(), movieId);
        }
        if (sessions.size() > 1) {
            Assertions.assertEquals(sessions.get(0).getRoomId(), sessions.get(1).getRoomId());
            Assertions.assertEquals(sessions.get(0).getMovieId(), sessions.get(1).getMovieId());
        }
    }

    @Test
    @DisplayName("Get Illegal Session And Movie")
    void GetIllegalSessionAndMovie() {
        var movieId = -1;
        var roomId = 2;
        var sessions = sessionDao.getSessionByMovieAndRoom(movieId, roomId);
        Assertions.assertEquals(sessions.size(), 0);

        movieId = 1;
        roomId = -2;
        sessions = sessionDao.getSessionByMovieAndRoom(movieId, roomId);
        Assertions.assertEquals(sessions.size(), 0);
    }

    @Test
    @DisplayName("Get Session By Movie")
    void getSessionsByMovie() {
        Integer movieId = 1;
        List<Session> sessions = sessionDao.getSessionsByMovie(movieId);
        Assertions.assertNotNull(sessions);
        if (sessions.size() > 0) {
            Assertions.assertEquals(sessions.get(0).getMovieId(), movieId);
        }
        if (sessions.size() > 1) {
            Assertions.assertEquals(sessions.get(0).getMovieId(), sessions.get(1).getMovieId());
        }
    }

    @Test
    @DisplayName("Get Illegal Session By Movie")
    void getIllegalSessionByMovie() {
        var movieId = -1;
        var sessions = sessionDao.getSessionsByMovie(movieId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());
    }

    @Test
    @DisplayName("Get Session By Id")
    void getSessionById() {
        Integer sessionId = 1;
        Optional<Session> session = sessionDao.getSessionById(sessionId);
        assertTrue(session.isPresent());
        Session session1 = session.get();
        Assertions.assertEquals(sessionId, session1.getId());
    }

    @Test
    @DisplayName("Get Session By Illegal Id")
    void getSessionByIllegalId() {
        var sessionId = -1;
        var session = sessionDao.getSessionById(sessionId);
        assertTrue(session.isEmpty());

        // Given
        Integer nullSessionId = null;

        // Then
        assertThrows(Exception.class, () -> {
            sessionDao.getSessionById(nullSessionId);
        });

        //assert (nullsession.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Session")
    void saveSession() {
        Session session = new Session();
        String seat = "legal seat";
        session.setSeat(seat);
        session.setRoomId(1);
        session.setMovieId(1);
        Session session1 = sessionDao.saveSession(session);
        Assertions.assertNotEquals(0, session1.getId());
        Assertions.assertEquals(seat, session1.getSeat());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Illegal Session")
    void saveIllegalSession() {
        Session session = new Session();
        assertThrows(DataIntegrityViolationException.class, () -> {
            sessionDao.saveSession(session);
        });
        //assertNull(savedSession);
    }
}