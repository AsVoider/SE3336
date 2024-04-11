package com.movie.backend.service;

import com.movie.backend.dto.CommentDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.entity.Comment;
import com.movie.backend.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {
    public List<MovieDTO> getAllMovies();
    public MovieDTO getMovie(Integer id);
    public void updateMovie(Map<String, String> params);
    List<CommentDTO> getCommentsByMovieId(Integer movieId);
    void saveComment(Integer userId, Integer movieId, String com);
}
