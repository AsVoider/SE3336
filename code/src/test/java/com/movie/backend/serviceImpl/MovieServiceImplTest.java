package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.Movie;
import com.movie.backend.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class MovieServiceImplTest {
    @Autowired
    MovieService movieService;

    @Test
    @DisplayName("getAllMovies")
    void getAllMovies() {
        var lists = movieService.getAllMovies();
        assert (!lists.isEmpty());
        assertEquals(lists.get(0).getTitle(), "流浪地球2");
    }

    @Test
    @DisplayName("getMovie")
    void getMovie() {
        var movieDto = movieService.getMovie(1);
        assertNotNull(movieDto);
        assertEquals(movieDto.getTitle(), "流浪地球2");
    }

    @Test
    @DisplayName("Get Movie Invalid")
    void getMovieInvalid() {
        var movieDto = movieService.getMovie(null);
        assertNull(movieDto);

        var movieDto1 = movieService.getMovie(123453);
        assertNull(movieDto1);
    }

    @Test
    @DisplayName("Update Movie")
    @Transactional
    @Rollback(value = true)
    void updateMovie() {
        var testMovie = movieService.getMovie(7);
        assertNotNull(testMovie);

        Map<String, String> map = new HashMap<>();
        map.put("title", "new title");
        map.put("intro", "new intro");
        map.put("id", String.valueOf(testMovie.getId()));

        movieService.updateMovie(map);
        var got = movieService.getMovie(7);
        assert (got.getIntro().equals("new intro") && got.getTitle().equals("new title"));
    }

    @Test
    @DisplayName("Update Invalid Movie")
    @Transactional
    @Rollback(value = true)
    void updateInvalidMovie() {
        var testMovie = movieService.getMovie(7);
        assertNotNull(testMovie);

        Map<String, String> map = null;
        assertThrows(NullPointerException.class, () -> {
            movieService.updateMovie(map);
        });
    }

    @Test
    @DisplayName("Get Comments By Movie Id")
    void getCommentsByMovieId() {
        var comments = movieService.getCommentsByMovieId(1);
        assert (!comments.isEmpty());

        assertEquals(comments.get(0).getComment(), "hehe");
        assertEquals(comments.get(1).getComment(), "haha");
    }

    @Test
    @DisplayName("Get Comments By Invalid Id")
    void getCommentsByInvalidId() {
        var comments = movieService.getCommentsByMovieId(124);
        assertEquals(comments.size(), 0);

        var comments1 = movieService.getCommentsByMovieId(-124);
        assertEquals(comments1.size(), 0);

        var comments2 = movieService.getCommentsByMovieId(null);
        assert (comments2.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Comment")
    void saveComment() {
        Integer id = 1;
        Integer movieId = 1;
        var com = "bbbbbb";
        movieService.saveComment(id, movieId, com);
        var comment = movieService.getCommentsByMovieId(movieId);
        assert (!comment.isEmpty());
        System.out.println(comment.size());
        for (var comm : comment) {
            if (comm.getMovieId() == 1 && comm.getUserid() == 1)
                assertEquals(comm.getComment(), "bbbbbb");
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Invalid Comment")
    void saveInvalidComment() {
        Integer id = -1;
        Integer movieId = 1;
        var com = "bbbbbb";
        assertThrows(DataIntegrityViolationException.class, () -> {
            movieService.saveComment(id, movieId, com);
        });

        Integer id1 = 1;
        Integer movieId1 = -1;
        var com1 = "asd";
        assertThrows(Exception.class, () -> {
            movieService.saveComment(id1, movieId1, com1);
        }); //todo this is wrong

        assertThrows(Exception.class, () -> {
            movieService.saveComment(1, null, null);
        });
    }
}