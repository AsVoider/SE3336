package com.movie.backend.controller;

import com.movie.backend.dto.CommentDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.entity.Comment;
import com.movie.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MovieController {
    @Autowired
    MovieService movieService;

    @RequestMapping("/getAllMovies")
    List<MovieDTO> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return movies;
    }


    @RequestMapping("/getMovie/{id}")
    MovieDTO getMovie(@PathVariable Integer id) {
        //System.out.println(id);
        MovieDTO movieDTO = movieService.getMovie(id);
        //influ3nza注：这一行没考虑到null的问题。
        //System.out.println(movieDTO.getTime());
        return movieDTO;
    }

    @RequestMapping("/updateMovie")
    void updateMovie(@RequestBody Map<String, String> params) {
        movieService.updateMovie(params);
    }

    @RequestMapping("/comment")
    void comment(@RequestBody Map<String, String> params) {
        Integer userId = Integer.parseInt(params.get("userId"));
        Integer movieId = Integer.parseInt(params.get("movieId"));
        String com = params.get("comment");
        movieService.saveComment(userId, movieId, com);
    }

    @RequestMapping("/getComments/{movieId}")
    List<CommentDTO> getComments(@PathVariable Integer movieId) {
        return movieService.getCommentsByMovieId(movieId);
    }
}
