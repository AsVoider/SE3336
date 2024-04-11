package com.movie.backend.daoImpl;

import com.movie.backend.dao.SessionDao;
import com.movie.backend.entity.Session;
import com.movie.backend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SessionDaoImpl implements SessionDao {
    @Autowired
    SessionRepository sessionRepository;
    @Override
    public List<Session> getSessionByMovieAndRoom(Integer movieId, Integer roomId) {
         return sessionRepository.findSessionsByMovieIdIsAndRoomIdIs(movieId, roomId);
    }

    @Override
    public List<Session> getSessionsByMovie(Integer movieId) {
        return sessionRepository.findSessionsByMovieIdIs(movieId);
    }

    @Override
    public Optional<Session> getSessionById(Integer sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        return sessionOptional;
    }

    @Override
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }
}
