package com.movie.backend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_rate", schema = "movieweb", catalog = "")
public class UserRate {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "movie_id")
    private Integer movieId;
    @Basic
    @Column(name = "rate")
    private Double rate;

    public UserRate(Integer userId, Integer movieId, Double rate) {
        this.userId = userId;
        this.movieId = movieId;
        this.rate = rate;
    }

    public UserRate() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRate userRate = (UserRate) o;
        return id == userRate.id && Objects.equals(userId, userRate.userId) && Objects.equals(movieId, userRate.movieId) && Objects.equals(rate, userRate.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, movieId, rate);
    }
}
