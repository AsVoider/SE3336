package com.movie.backend.serviceImpl;

import com.movie.backend.entity.UserRate;
import com.movie.backend.repository.UserRateRepository;
import com.movie.backend.service.RecommendService;
import com.movie.backend.utils.RecommendationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    UserRateRepository userRateRepository;

    private final int TRIGGER = 10;

    private final int RECOMMEND_NUM = 3;


    private void generateTmpFile(String filename) {
        List<UserRate> userRates = userRateRepository.findAll();

        File file = new File(filename);
        try (FileWriter fw = new FileWriter(file, false)) {
            for (UserRate userRate : userRates) {
                fw.write(userRate.getUserId() + "," + userRate.getMovieId() + "," + userRate.getRate() + "\n");
            }
        } catch (IOException e) {
            if (file.exists())
                file.delete();
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getRecommendMovies(Integer userId) {
        File file = new File(System.getProperty("java.io.tmpdir"), "recommendation.out");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Integer> movieIds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strs = line.split(" ");
                Integer userIdTmp = Integer.parseInt(strs[0]);
                if (strs.length == 1)
                    continue;
                if (userIdTmp.equals(userId)) {
                    for (int i = 1; i < strs.length; ++i) {
                        String str = strs[i];
                        if (str.isEmpty())
                            continue;
                        movieIds.add(Integer.parseInt(strs[i]));
                    }
                    break;
                }
            }
            return movieIds;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void saveUserRate(UserRate userRate) {
        Optional<UserRate> userRateOptional = userRateRepository.findByUserIdAndMovieId(userRate.getUserId(), userRate.getMovieId());
        if (userRateOptional.isPresent()) {
            UserRate userRateTmp = userRateOptional.get();
            userRateTmp.setRate(userRate.getRate());
            userRateRepository.save(userRateTmp);
        } else {
            userRateRepository.save(userRate);
        }
        String filename = System.getProperty("java.io.tmpdir") + "ratings.tmp";
        generateTmpFile(filename);
        try {
            RecommendationUtil.getItemBasedRecommendation(filename, RECOMMEND_NUM);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        long count = userRateRepository.count();
//        if (count % TRIGGER == 0) {
//            String filename = System.getProperty("java.io.tmpdir") + "ratings.tmp";
//            generateTmpFile(filename);
//            try {
//                RecommendationUtil.getItemBasedRecommendation(filename, RECOMMEND_NUM);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public double getRate(Integer userId, Integer movieId) {
        Optional<UserRate> userRate = userRateRepository.findByUserIdAndMovieId(userId, movieId);
        if (userRate.isPresent()) {
            return userRate.get().getRate();
        } else {
            return 0;
        }
    }
}
