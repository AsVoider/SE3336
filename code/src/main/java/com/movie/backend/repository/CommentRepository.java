package com.movie.backend.repository;

import com.movie.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByMovieIdIs(Integer movieId);
    Comment findCommentByMovieIdIsAndUseridIs(Integer movieId, Integer userId);
}