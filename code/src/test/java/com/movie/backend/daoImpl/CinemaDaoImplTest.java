package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.CinemaDao;
import com.movie.backend.entity.Cinema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = BackendApplication.class)
class CinemaDaoImpTest {
    private CinemaDao cinemaDao;

    @Autowired
    public CinemaDaoImpTest(CinemaDao cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    @Test
    void getAllTest() {
        var allCinema = cinemaDao.getAllCinemas();

    }
}


//@SpringBootTest(classes = BackendApplication.class)
//class CinemaDaoImplTest {
//    @Autowired
//    private CinemaDao cinemaDao;
//
//    @Test
//    @DisplayName("getCinemaById legal id")
//    void getCinemaById() {
//        Cinema cinema = cinemaDao.getCinemaById(1);
//        Cinema cinema1 = new Cinema();
//        cinema1.setId(1);
//        cinema1.setName("上海巨影Suprinema（万乐城店）");
//        Assertions.assertNotNull(cinema);
//        Assertions.assertEquals(cinema1.getName(), cinema.getName());
//    }
//
//    @Test
//    @DisplayName("getCinemaById illegal id")
//    void getCinemaByIdIllegal() {
//        Cinema cinema = cinemaDao.getCinemaById(-1);
//        Assertions.assertNull(cinema);
//    }
//
//    @Test
//    @DisplayName("getCinemaById null")
//    void getCinemaNull() {
//        Cinema cinema = cinemaDao.getCinemaById(100);
//        Assertions.assertNull(cinema);
//    }
//
//    @Test
//    @DisplayName("get all movies")
//    void getAllCinemas() {
//        List<Cinema> cinemas = cinemaDao.getAllCinemas();
//        Assertions.assertNotNull(cinemas);
//        Assertions.assertNotEquals(0, cinemas.size());
//    }
//}