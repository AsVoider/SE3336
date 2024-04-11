package com.movie.backend.dao;

import com.movie.backend.entity.Movie;

import java.util.List;

public interface MovieDao {
    public List<Movie> getMovies();
    public Movie getMovie(Integer id);
    Movie saveMovie(Movie movie);
}
