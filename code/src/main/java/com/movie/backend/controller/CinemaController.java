package com.movie.backend.controller;

import com.movie.backend.entity.Cinema;
import com.movie.backend.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @RequestMapping("/getAllCinemas")
    public List<Cinema> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

//    @RequestMapping("/getCinemas/{movieId}")
    @GetMapping("/getCinemas/{movieId}")
    public List<Cinema> getCinemasByMovieId(@PathVariable Integer movieId) {
//        System.out.print("the val of movieId: ");
//        System.out.println(movieId);
        if (movieId == null) return new ArrayList<>();
        return cinemaService.getCinemasByMovieId(movieId);
    }

    @GetMapping("/getCinema/{cinemaId}")
    public Cinema getCinemaById(@PathVariable Integer cinemaId) {
        return cinemaService.getCinemaById(cinemaId);
    }

    @RequestMapping("/updateCinema")
    public void updateCinema(@RequestBody Map<String, String> params) {
        cinemaService.updateCinema(params);
    }
}
