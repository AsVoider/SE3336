package com.movie.backend.dao;

import com.movie.backend.entity.Comment;

import java.util.List;

public interface CommentDao {
    Comment save(Comment comment);
    List<Comment> getCommentsByMovieId(Integer movieId);
    Comment getCommentByMovieIdAndUserId(Integer movieId, Integer userId);
}
