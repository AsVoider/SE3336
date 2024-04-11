package com.example.keytech.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue
    private Long id;

    private Long movieId;

    private Integer rows;

    private Integer cols;

    private String seats;

    public Session(Long movieId, Integer rows, Integer cols, String seats) {
        this.movieId = movieId;
        this.rows = rows;
        this.cols = cols;
        this.seats = seats;
    }
}
