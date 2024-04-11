package com.movie.backend.dao;

import com.movie.backend.entity.User;

public interface UserDao {
    Boolean existsUserByIdIs(Integer userId);
    User save(User user);
    User findUserByUsername(String userName);
    User getUserById(Integer uid);
}
