package com.movie.backend.repository;

import com.movie.backend.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    Cinema findCinemaByIdIs(Integer cinemaId);
}