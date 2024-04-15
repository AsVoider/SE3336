package com.movie.backend.serviceImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.entity.UserRate;
import com.movie.backend.service.RecoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = BackendApplication.class)
class RecommendServiceImplTest {
    @Autowired
    RecoService recoService;

    @Test
    @DisplayName("generate temp file")
    void generateTemp() {
        //create first time
        recoService.generateTmpFile("1.tmp");

        // create again
        recoService.generateTmpFile("1.tmp");
    }

    @Test
    @DisplayName("save user rate")
    @Transactional
    @Rollback(value = true)
    void saveUserRate() {
        UserRate userRate = new UserRate(1, 4, 5.5);
        recoService.saveUserRate(userRate);

        // save again
        recoService.saveUserRate(userRate);

        List<Integer> list = recoService.getRecommendMovies(1);
        Assertions.assertNotNull(list);

        userRate.setMovieId(1);
        recoService.saveUserRate(userRate);
    }

    @Test
    @DisplayName("get user rate")
    @Transactional
    @Rollback(value = true)
    void getUserRate() {
        UserRate userRate = new UserRate(1, 4, 5.5);
        recoService.saveUserRate(userRate);
        Double d = recoService.getRate(1, 4);
        Assertions.assertEquals(5.5, d);

        //userRate.setMovieId(1);
        d = recoService.getRate(1, 1);
        Assertions.assertEquals(d, 0);
    }
}
