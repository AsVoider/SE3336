package com.movie.backend.serviceImpl;

import com.movie.backend.dao.*;
import com.movie.backend.dto.CommentDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.entity.*;
import com.movie.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieDao movieDao;
    @Autowired
    ActorDao actorDao;
    @Autowired
    DirectorDao directorDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;
    private MovieDTO transMovieIntoDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        if (movie == null) return null;
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setTime(movie.getTime());
        String[] directors = movie.getDirector().split(" ");
        List<Director> directorList = new ArrayList<>();
        for (String director: directors) {
            Director director1 = directorDao.getDirectorByName(director);
            if (director1 != null) {
                directorList.add(director1);
            }
        }

        movieDTO.setDirectors(directorList);
        String[] actors = movie.getActor().split(" ");
        List<Actor> actorList = new ArrayList<>();
        for (String actor : actors) {
            Actor actor1 = actorDao.getActorByName(actor);
            if (actor1 != null) {
                actorList.add(actor1);
            }
        }
        movieDTO.setActors(actorList);
        movieDTO.setMark(movie.getMark());
        movieDTO.parseTypes(movie);
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setSrc(movie.getSrc());
        movieDTO.setIntro(movie.getIntro());
        return movieDTO;
    }

    private CommentDTO transCommentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment.getComment());
        commentDTO.setMovieId(comment.getMovieId());
        commentDTO.setUserid(comment.getUserid());
        Integer userId = comment.getUserid();
        User user = userDao.getUserById(userId);
        commentDTO.setUsername(user.getUsername());
        return commentDTO;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieDao.getMovies();
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (int i = 0; i < movies.size(); ++i) {
            MovieDTO movieDTO = transMovieIntoDTO(movies.get(i));
            movieDTOS.add(movieDTO);
        }
        return movieDTOS;
    }

    @Override
    public MovieDTO getMovie(Integer id) {
        if (id == null) return null;
        Movie movie = movieDao.getMovie(id);
        MovieDTO movieDTO = transMovieIntoDTO(movie);
        return movieDTO;
    }

    @Override
    public void updateMovie(Map<String, String> params) {
        Integer movieId = Integer.parseInt(params.get("id"));
        Movie movie = movieDao.getMovie(movieId);
        String title = params.get("title");
        String intro = params.get("intro");
        movie.setTitle(title);
        movie.setIntro(intro);
        movieDao.saveMovie(movie);
    }

    @Override
    public List<CommentDTO> getCommentsByMovieId(Integer movieId) {
        List<Comment> comments = commentDao.getCommentsByMovieId(movieId);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (int i = 0; i < comments.size(); ++i) {
            CommentDTO commentDTO = transCommentToDTO(comments.get(i));
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }

    @Override
    public void saveComment(Integer userId, Integer movieId, String com) {
        Comment comment = commentDao.getCommentByMovieIdAndUserId(movieId, userId);
        if (comment == null) {
            comment = new Comment();
            comment.setMovieId(movieId);
            comment.setUserid(userId);
        }
        comment.setComment(com);
        commentDao.save(comment);
    }
}
