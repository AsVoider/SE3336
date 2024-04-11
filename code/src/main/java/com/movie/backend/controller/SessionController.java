package com.movie.backend.controller;

import com.movie.backend.entity.Session;
import com.movie.backend.repository.SessionRepository;
import com.movie.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SessionController {
    @Autowired
    SessionService sessionService;
    @Autowired
    private SessionRepository sessionRepository;

    @RequestMapping("/getSessions/{movieId}/{cinemaId}")
    public List<Session> getSessions
            (@PathVariable Integer movieId,
             @PathVariable Integer cinemaId) {
        if (movieId == null || cinemaId == null) return new ArrayList<>();
        return sessionService.getSessionsByMovieAndCinema(movieId, cinemaId);
    }

    @GetMapping("/getSession/{sessionId}")
    public Session getSession(@PathVariable Integer sessionId) {
        return sessionService.getSessionById(sessionId);
    }

    @RequestMapping("/updateSession")
    public void updateSession(@RequestBody Map<String, String> params) {
        sessionService.updateSession(params);
    }
    @RequestMapping("/getAllSessions")
    public List<Session> getAllSessions() {
        return  sessionRepository.findAll();
    }
}
