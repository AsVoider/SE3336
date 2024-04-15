package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dto.UserStatus;
import com.movie.backend.entity.User;
import com.movie.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = BackendApplication.class)
public class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("test register")
    void register() {
        Boolean a = userService.register("a", "aaa", 0);

        Map<String, String> params = new HashMap<>();
        params.put("username", "b");
        params.put("password", "bbb");
        params.put("gender", "0");
        params.put("area", "aa");
        params.put("intro", "a");
        params.put("email", "a");
        params.put("phone", "a");
        params.put("birthday", "1111-11-11");

        Boolean b = userService.register(params);
    }

    @Test
    @DisplayName("test login")
    void login() {
        UserStatus userStatus = userService.login("user1", "12345");
        Assertions.assertNotNull(userStatus);
        Assertions.assertEquals(2, userStatus.getUserId());

        UserStatus userStatus1 = userService.login("user11", "12345");
        Assertions.assertNotNull(userStatus1);
        Assertions.assertEquals(-1, userStatus1.getUserId());
    }

    @Test
    @DisplayName("get user by Id")
    void getById() {
        User user = userService.getUserById(1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());

        User user1 = userService.getUserById(-1);
        Assertions.assertNull(user1);
    }
}
