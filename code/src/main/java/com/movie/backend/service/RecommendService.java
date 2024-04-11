package com.movie.backend.service;

import com.movie.backend.entity.UserRate;

import java.util.List;

public interface RecommendService {
    List<Integer> getRecommendMovies(Integer userId);

    void saveUserRate(UserRate userRate);

    double getRate(Integer userId, Integer movieId);
}
