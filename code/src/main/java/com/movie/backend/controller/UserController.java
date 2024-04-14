package com.movie.backend.controller;

import com.movie.backend.constant.Constant;
import com.movie.backend.dto.UserStatus;
import com.movie.backend.entity.User;
import com.movie.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/register")
    Boolean register(@RequestBody Map<String, String> params) {
//        String userName = params.get(Constant.USER_NAME);
//        String name = params.get(Constant.NAME);
//        String password = params.get(Constant.PASSWORD);
//        String email = params.get(Constant.EMAIL);
//        String phone = params.get(Constant.PHONE);
//        String genderStr = params.get(Constant.GENDER);
//        Integer gender = Integer.parseInt(genderStr);
        //userService.register(userName, password, gender);
        userService.register(params);
        return true;
    }
    @GetMapping("/login/{username}/{password}")
    UserStatus login(@PathVariable String username,
                     @PathVariable String password) {
        System.out.println(username);
        return userService.login(username, password);
    }

    @GetMapping("/getUser/{userId}")
    User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }
}
