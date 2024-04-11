package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Movie;
import com.movie.backend.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class MovieServiceImplTest {
    @Autowired
    MovieService movieService;

    MovieServiceImpl movieServiceImpl = new MovieServiceImpl();

    //利用反射对私有方法进行单元测试
//    @Test
//    @DisplayName("test private parse method")
//    void parseTypes() {
//        Movie movie = new Movie();
//        long type = 1;
//        movie.setType(type);
//        ReflectionTestUtils.invokeMethod(movieServiceImpl, "parseTypes", movie);
//        Assertions.assertNotNull(movie.getTypes());
//        Assertions.assertEquals("科幻", movie.getTypes().get(0));
//    }
//
//    @Test
//    @DisplayName("test get all movies")
//    void getAllMovies() {
//        List<Movie> movies = movieService.getAllMovies();
//        Assertions.assertNotNull(movies.size());
//    }
//
//    @Test
//    @DisplayName("test get movie by id")
//    void getMovie() {
//        //测试正常id，以及解析type
//        Integer movieId = 1;
//        Movie movie = movieService.getMovie(movieId);
//        Assertions.assertNotNull(movie);
//        Assertions.assertNotNull(movie.getTypes());
//
//        //电影id不存在
//        movieId = -1;
//        movie = movieService.getMovie(movieId);
//        Assertions.assertNull(movie);
//
//        //非法输入
//        movieId = null;
//        movie = movieService.getMovie(movieId);
//        Assertions.assertNull(movie);
//    }
}