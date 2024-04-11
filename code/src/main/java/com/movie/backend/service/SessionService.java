package com.movie.backend.service;

import com.movie.backend.entity.Session;

import java.util.List;
import java.util.Map;

public interface SessionService {
    List<Session> getSessionsByMovieAndCinema(Integer movieId, Integer cinemaId);
    Session getSessionById(Integer sessionId);
    void updateSession(Map<String, String> params);


}
