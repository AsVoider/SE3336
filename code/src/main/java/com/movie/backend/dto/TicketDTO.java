package com.movie.backend.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class TicketDTO {
    private Integer id;

    private Integer orderId;

    private Integer sessionId;

    private Integer row;

    private Integer col;

    private BigDecimal price;

    private Integer state;

    private String movieName;

    private String cinemaName;


    private String roomName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return Objects.equals(id, ticketDTO.id) && Objects.equals(orderId, ticketDTO.orderId) && Objects.equals(sessionId, ticketDTO.sessionId) && Objects.equals(row, ticketDTO.row) && Objects.equals(col, ticketDTO.col) && Objects.equals(price, ticketDTO.price) && Objects.equals(state, ticketDTO.state) && Objects.equals(movieName, ticketDTO.movieName) && Objects.equals(cinemaName, ticketDTO.cinemaName) && Objects.equals(roomName, ticketDTO.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, sessionId, row, col, price, state, movieName, cinemaName, roomName);
    }


}
