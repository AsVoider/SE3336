package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.CinemaDao;
import com.movie.backend.dao.RoomDao;
import com.movie.backend.dao.SessionDao;
import com.movie.backend.entity.Cinema;
import com.movie.backend.service.CinemaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class)
class CinemaServiceImplTest {
    @Mock
    CinemaDao cinemaDao;

    @Mock
    RoomDao roomDao;

    @Mock
    SessionDao sessionDao;

    @Autowired
    CinemaService cinemaService;

    @InjectMocks
    CinemaService cinemaServiceMocked = new CinemaServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        var cinema4test = new Cinema();
        cinema4test.setId(1);
        cinema4test.setSrc("test");
        cinema4test.setMinPrice(BigDecimal.ONE);
        cinema4test.setAddress("test");
        cinema4test.setLocation("test");
        cinema4test.setName("test");
        when(cinemaDao.getCinemaById(1)).thenReturn(cinema4test);
        //when(cinemaDao.getCinemaById()).thenReturn(null);
    }

    @Test
    @DisplayName("get all the cinemas")
    void getAllCinemas() {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(cinemas.get(0).getName(), "上海巨影Suprinema（万乐城店）");
        Assertions.assertEquals(cinemas.get(1).getName(), "AMG海上明珠影城（上海环球港RealDCinema店）");
        Assertions.assertNotEquals(0, cinemas.size());
    }

    @Test
    @DisplayName("get cinema by movie id")
    void getCinemasByMovieId() {
        //movieid存在
        Integer movieId = 1;
        List<Cinema> cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        if (cinemas.size() > 0) {
            Assertions.assertNotNull(cinemas.get(0).getMinPrice());
        }
    }

    @Test
    @DisplayName("Nonexist Movie Id But No Cinema")
    void getCinemaNonExist() {
        //movieid存在，但无相应的影院
        var movieId = 2;
        var cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());
    }

    @Test
    @DisplayName("Invalid movieId")
    void getCinemaByInvalidMovieId() {
        //movieid不存在
        Integer movieId = 8;
        var cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());

        //movieId非法
        movieId = -2;
        cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());

        //mnovieId为空
        movieId = null;
        cinemas = cinemaService.getCinemasByMovieId(movieId);
        Assertions.assertNotNull(cinemas);
        Assertions.assertEquals(0, cinemas.size());
    }

    @Test
    @DisplayName("Get Cinema By Id")
    void getCinemaById() {
        var id = 1;
        var res = cinemaServiceMocked.getCinemaById(id);
        assertEquals(res.getName(), "test");
    }

    @Test
    @DisplayName("Get Cinema By Invalid Id")
    void getCinemaByInvalidId() {
        var id = 999;
        when(cinemaDao.getCinemaById(anyInt())).thenReturn(null);

        var res1 = cinemaServiceMocked.getCinemaById(id);

        assertNull(res1);

        //when(cinemaDao.getCinemaById(null)).thenReturn(null);

        Integer id1 = null;

        res1 = cinemaServiceMocked.getCinemaById(id1);

        assertNull(res1);
    }

    @Test
    @DisplayName("Update")
    void updateCinema() {
        var cinema = new Cinema();
        cinema.setId(2);
        cinema.setSrc("test");
        cinema.setMinPrice(BigDecimal.ONE);
        cinema.setAddress("tt");
        cinema.setLocation("test");
        cinema.setName("new name");
        when(cinemaDao.save(cinema)).thenReturn(cinema);
        when(cinemaDao.getCinemaById(2)).thenReturn(cinema);

        Map<String, String> map = new HashMap<>();

        map.put("id", "1");
        map.put("name", "new name");
        map.put("address", "tt");
        cinemaServiceMocked.updateCinema(map);
        var got = cinemaServiceMocked.getCinemaById(2);
        assertEquals(got.getName(), "new name");
        //这个咋mock？ 没有返回值
    }
}