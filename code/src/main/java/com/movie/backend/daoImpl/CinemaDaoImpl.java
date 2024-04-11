package com.movie.backend.daoImpl;

import com.movie.backend.dao.CinemaDao;
import com.movie.backend.entity.Cinema;
import com.movie.backend.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CinemaDaoImpl implements CinemaDao {
    @Autowired
    CinemaRepository cinemaRepository;
    @Override
    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema getCinemaById(Integer cinemaId) {
        return cinemaRepository.findCinemaByIdIs(cinemaId);
    }

    @Override
    public Cinema save(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }


}
