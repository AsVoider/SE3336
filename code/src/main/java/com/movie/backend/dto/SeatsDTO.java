package com.movie.backend.dto;


import com.movie.backend.entity.Room;
import com.movie.backend.entity.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatsDTO {
    private Integer userId;

    private Integer sessionId;

    private List<Integer> seats;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatsDTO seatsDTO = (SeatsDTO) o;
        return Objects.equals(userId, seatsDTO.userId) && Objects.equals(sessionId, seatsDTO.sessionId) && Objects.equals(seats, seatsDTO.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionId, seats);
    }

    @Override
    public String toString() {
        return "SeatsDTO{" +
                "userId=" + userId +
                ", sessionId=" + sessionId +
                ", seats=" + seats +
                '}';
    }
}
