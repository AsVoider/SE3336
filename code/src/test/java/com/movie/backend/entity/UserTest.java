package com.movie.backend.entity;

import com.movie.backend.BackendApplication;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class UserTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("test user's getter and setter")
    public void testAll() {
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(user1.getId());
        user1.setName("111");
        user2.setName(user1.getName());
        user1.setGender(1);
        user2.setGender(user1.getGender());
        Date date = new Date(System.currentTimeMillis());
        user1.setBirthday(date);
        user2.setBirthday(user1.getBirthday());
        user1.setArea("1111");
        user2.setArea(user1.getArea());
        user1.setIntro("user");
        user2.setIntro(user1.getIntro());
        user1.setRole(1);
        user2.setRole(user1.getRole());
        Assertions.assertEquals(true, user1.equals(user2));
        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();
        Assertions.assertEquals(hash1, hash2);
    }
}