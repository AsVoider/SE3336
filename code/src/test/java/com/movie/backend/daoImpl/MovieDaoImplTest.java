package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.MovieDao;
import com.movie.backend.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class MovieDaoImplTest {
    @Autowired
    MovieDao movieDao;

    @Test
    @DisplayName("get movies")
    void getMovies() {
        List<Movie> movies = movieDao.getMovies();
        Assertions.assertNotNull(movies);
        Assertions.assertNotEquals(0, movies.size());
    }

    @Test
    @DisplayName("get movie id legally")
    void getMovie() {
        Movie movie = movieDao.getMovie(1);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(1, movie.getId());
        Assertions.assertEquals("流浪地球2", movie.getTitle());
    }

    @Test
    @DisplayName("get null")
    void getMovieNull() {
        Movie movie = movieDao.getMovie(-1);
        Assertions.assertNull(movie);
    }
}