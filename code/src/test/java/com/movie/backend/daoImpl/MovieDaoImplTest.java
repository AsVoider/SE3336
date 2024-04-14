package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.MovieDao;
import com.movie.backend.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class MovieDaoImplTest {
    @Autowired
    MovieDao movieDao;

    @Test
    @DisplayName("Get Movies")
    void getMoviesTest() {
        List<Movie> movies = movieDao.getMovies();
        Assertions.assertNotNull(movies);
        Assertions.assertNotEquals(0, movies.size());
        Assertions.assertEquals(movies.get(0).getTitle(), "流浪地球2");
        Assertions.assertEquals(movies.get(4).getActor(), "原菜乃华 松村北斗 深津绘里");
    }

    @Test
    @DisplayName("Get Movie By Id Legally")
    void getMovieTest() {
        Movie movie = movieDao.getMovie(1);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(1, movie.getId());
        Assertions.assertEquals("流浪地球2", movie.getTitle());
        Assertions.assertEquals(movie.getActor(), "吴京 刘德华 李雪健 沙溢");
    }

    @Test
    @DisplayName("Get Null Movie")
    void getMovieNull() {
        Movie movie = movieDao.getMovie(-1);
        Assertions.assertNull(movie);
    }

    @Test
    @DisplayName("Save Movie")
    @Transactional
    @Rollback(value = true)
    void saveMovieTest() {
        var mv = new Movie();
        mv.setActor("test");
        mv.setDirector("test");
        mv.setSrc("test");
        mv.setDuration(new Time(2, 1, 1));
        mv.setIntro("test");
        mv.setMark(BigDecimal.valueOf(5.0));
        mv.setTitle("test");
        mv.setType(2L);
        mv.setId(7);
        var saved = movieDao.saveMovie(mv);
        var got = movieDao.getMovie(7);
        Assertions.assertEquals(saved, got);
    }

    @Test
    @DisplayName("Save Invalid Movie")
    @Transactional
    void saveInvalidTest() {
        var mv = new Movie();
        Movie saved = null;
        try {
            saved = movieDao.saveMovie(mv);
        } catch (Exception e) {

        }
        //var got = movieDao.getMovie(7);
        assertNull(saved);
    }
}