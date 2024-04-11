package com.movie.backend.repository;

import com.movie.backend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {
     List<Session> findSessionsByMovieIdIsAndRoomIdIs(Integer movieId, Integer roomId);

     List<Session> findSessionsByMovieIdIs(Integer movieId);

     Session findSessionByIdIs(Integer sessionId);

//    void getAllSessions();
}