package com.movie.backend.daoImpl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.support.geo.Point;
import com.movie.backend.BackendApplication;
import com.movie.backend.dao.CinemaDao;
import com.movie.backend.entity.Cinema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = BackendApplication.class)
class CinemaDaoImpTest {
    private final CinemaDao cinemaDao;

    @Autowired
    public CinemaDaoImpTest(CinemaDao cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    @Test
    @DisplayName(value = "Get All Test")
    void getAllTest() {
        var allCinema = cinemaDao.getAllCinemas();
        for (int i = 0; i < allCinema.size(); i++) {
            if (i == 0)
                Assertions.assertEquals(allCinema.get(i).getName(), "上海巨影Suprinema（万乐城店）");
            if (i == 1)
                Assertions.assertEquals(allCinema.get(i).getName(), "AMG海上明珠影城（上海环球港RealDCinema店）");
            if (i == 2)
                Assertions.assertEquals(allCinema.get(i).getName(), "幸福蓝海国际影城（彩生活店）");
        }
    }

    @Test
    @DisplayName(value = "Get Cinema Test")
    void getCinemaTest() {
        var nomarlCinema = cinemaDao.getCinemaById(1);
        var allCinema = cinemaDao.getAllCinemas();
        Assertions.assertEquals(nomarlCinema, allCinema.get(0));

        var testCinema = cinemaDao.getCinemaById(4);
        Point location = JSONObject.parseObject(testCinema.getLocation(), Point.class);
        System.out.println(JSONObject.from(location).toJSONString());
        assertEquals(JSONObject.toJSONString(location), "{\"type\":\"Point\",\"bbox\":[123.3,0.34],\"coordinates\":[10.0,12.3]}");
    }

    @Test
    @DisplayName(value = "Get Invalid Cinema Test")
    void getCinemaInvalidTest() {
        var badCinema = cinemaDao.getCinemaById(-123);
        assert (badCinema == null);
    }

    @Test
    @DisplayName("Save Test")
    void saveTest1() {
        var cinema = new Cinema();
        cinema.setId(4);
        cinema.setAddress("test");
        cinema.setDistance(123);
        cinema.setName("test");
        Point geometry = new Point();
        geometry.setLatitude(12.3);
        geometry.setLongitude(10.0);
        double[] bbox = {123.3, 0.34};
        geometry.setBbox(bbox);
        cinema.setLocation(JSONObject.toJSONString(geometry));
        cinema.setSrc("test");
        cinema.setMinPrice(BigDecimal.valueOf(123.3));
        var savedCinema = cinemaDao.save(cinema);
        var all = cinemaDao.getAllCinemas();
        var lastCinema = all.get(all.size() - 1);
        assertEquals(lastCinema, savedCinema);
    }

    @Test
    @DisplayName("Save Invalid Test")
    void saveInvalidTest() {
        var cinema = cinemaDao.getCinemaById(4);
        Assertions.assertNotNull(cinema);
        cinema.setSrc(null);
        cinema.setLocation(null);
        cinema.setDistance(null);
        cinema.setAddress(null);
        cinema.setMinPrice(null);
        try {
            cinemaDao.save(cinema);
        } catch (Exception e) {
            assert (true);
        }
    }
}
