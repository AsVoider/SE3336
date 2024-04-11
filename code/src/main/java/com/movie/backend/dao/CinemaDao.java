package com.movie.backend.dao;

import com.movie.backend.entity.Cinema;

import java.util.List;

public interface CinemaDao {
    List<Cinema> getAllCinemas();
    Cinema getCinemaById(Integer cinemaId);
    Cinema save(Cinema cinema);
}
