package com.movie.backend.daoImpl;

import com.movie.backend.dao.MovieDao;
import com.movie.backend.entity.Movie;
import com.movie.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {
    @Autowired
    MovieRepository movieRepository;
    @Override
    public List<Movie> getMovies() {
         return movieRepository.findAll();
    }

    @Override
    public Movie getMovie(Integer id) {
        return movieRepository.findMovieByIdIs(id);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
