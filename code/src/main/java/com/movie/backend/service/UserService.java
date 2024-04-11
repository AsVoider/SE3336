package com.movie.backend.service;

import com.movie.backend.dto.UserStatus;
import com.movie.backend.entity.User;

import java.util.Map;

public interface UserService {
    public Boolean register(String userName, String password, Integer gender);
    public Boolean register(Map<String, String> params);
    public UserStatus login(String userName, String password);
    User getUserById(Integer uid);
}
