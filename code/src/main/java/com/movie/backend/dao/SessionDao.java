package com.movie.backend.dao;

import com.movie.backend.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionDao {
    List<Session> getSessionByMovieAndRoom(Integer movieId, Integer roomId);

    List<Session> getSessionsByMovie(Integer movieId);

    Optional<Session> getSessionById(Integer sessionId);

    Session saveSession(Session session);
}
