package com.movie.backend.repository;

import com.movie.backend.entity.User;
import com.movie.backend.entity.UserRate;
import org.apache.zookeeper.Op;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRateRepository extends JpaRepository<UserRate, Integer> {
    Optional<UserRate> findByUserId(Integer userId);

    Optional<UserRate> findByUserIdAndMovieId(Integer userId, Integer movieId);
}
