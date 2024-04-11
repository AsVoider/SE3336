package com.movie.backend.daoImpl;

import com.movie.backend.dao.CommentDao;
import com.movie.backend.entity.Comment;
import com.movie.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByMovieId(Integer movieId) {
        return commentRepository.findCommentsByMovieIdIs(movieId);
    }

    @Override
    public Comment getCommentByMovieIdAndUserId(Integer movieId, Integer userId) {
        return commentRepository.findCommentByMovieIdIsAndUseridIs(movieId, userId);
    }
}
