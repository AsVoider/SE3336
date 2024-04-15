package com.movie.backend.controller;

import com.movie.backend.dto.MovieDTO;
import com.movie.backend.entity.Movie;
import com.movie.backend.entity.UserRate;
import com.movie.backend.service.MovieService;
import com.movie.backend.service.RecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RecommendController {
    @Autowired
    RecoService recommendService;

    @Autowired
    MovieService movieService;

    @PostMapping("/getRate")
    ResponseEntity<?> getRate(@RequestParam Integer userId, @RequestParam Integer movieId) {
        return ResponseEntity.ok(Map.of("rate", recommendService.getRate(userId, movieId)));
    }

    @PostMapping("/rate")
    ResponseEntity<?> rate(@RequestBody Map<String, Object> body) {
        Integer userId = (Integer) body.get("userId");
        Integer movieId = (Integer) body.get("movieId");
//        Double rate = (Double) body.get("rate");
        String rateStr = (String) body.get("rate");
        Double rate = Double.parseDouble(rateStr);
        recommendService.saveUserRate(new UserRate(userId, movieId, rate));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recommend/{userId}")
    List<MovieDTO> recommend(@PathVariable Integer userId) {
        List<Integer> movieIds = recommendService.getRecommendMovies(userId);
        if (movieIds == null || movieIds.size() == 0) {
            return movieService.getAllMovies();
        }
        List<MovieDTO> movies = new ArrayList<>();
        for (Integer movieId : movieIds) {
            movies.add(movieService.getMovie(movieId));
        }
        return movies;
    }
}
