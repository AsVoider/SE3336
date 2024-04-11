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
    @DisplayName("get session and movie")
    void getSessionByMovieAndRoom() {
        //检查合法的数据
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

        //检查非法的数据
        movieId = -1;
        roomId = 2;
        sessions = sessionDao.getSessionByMovieAndRoom(movieId, roomId);
        Assertions.assertEquals(sessions.size(), 0);

        movieId = 1;
        roomId = -2;
        sessions = sessionDao.getSessionByMovieAndRoom(movieId, roomId);
        Assertions.assertEquals(sessions.size(), 0);
    }

    @Test
    @DisplayName("get session by movie")
    void getSessionsByMovie() {
        //检查合法数据
        Integer movieId = 1;
        List<Session> sessions = sessionDao.getSessionsByMovie(movieId);
        Assertions.assertNotNull(sessions);
        if (sessions.size() > 0) {
            Assertions.assertEquals(sessions.get(0).getMovieId(), movieId);
        }
        if (sessions.size() > 1) {
            Assertions.assertEquals(sessions.get(0).getMovieId(), sessions.get(1).getMovieId());
        }

        //检查非法数据
        movieId = -1;
        sessions = sessionDao.getSessionsByMovie(movieId);
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(0, sessions.size());
    }

    @Test
    @DisplayName("get session by id")
    void getSessionById() {
        Integer sessionId = 1;
        Optional<Session> session = sessionDao.getSessionById(sessionId);
        Assertions.assertEquals(true, session.isPresent());
        Session session1 = session.get();
        Assertions.assertEquals(sessionId, session1.getId());

        //检查非法数据
        sessionId = -1;
        session = sessionDao.getSessionById(sessionId);
        Assertions.assertEquals(true, session.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save session")
    void saveSession() {
        Session session = new Session();
        String seat = "illegal seat";
        session.setSeat(seat);
        session.setRoomId(1);
        session.setMovieId(1);
        Session session1 = sessionDao.saveSession(session);
        Assertions.assertNotEquals(0, session1.getId());
        Assertions.assertEquals(seat, session1.getSeat());
    }
}