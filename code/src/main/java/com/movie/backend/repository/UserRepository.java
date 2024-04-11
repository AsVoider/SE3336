package com.movie.backend.repository;

import com.movie.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsUserByIdIs(Integer userId);
    User findUserByIdIs(Integer uid);
    User findUserByUsernameIs(String userName);
}