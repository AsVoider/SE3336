package com.movie.backend.service;

import com.movie.backend.entity.Cinema;

import java.util.List;
import java.util.Map;

public interface CinemaService {
    public List<Cinema> getAllCinemas();

    public List<Cinema> getCinemasByMovieId(Integer movieId);
    Cinema getCinemaById(Integer cinemaId);
    void updateCinema(Map<String, String> params);
}
