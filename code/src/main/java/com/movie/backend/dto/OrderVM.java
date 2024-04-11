package com.movie.backend.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

public class OrderVM {
    private Integer id;

    private Timestamp time;

    private Integer userId;

    private BigDecimal totalPrice;

    private Byte isPaid;

    private Set<TicketDTO> tickets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Byte getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Byte isPaid) {
        this.isPaid = isPaid;
    }

    public Set<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderVM orderVM = (OrderVM) o;
        return Objects.equals(id, orderVM.id) && Objects.equals(time, orderVM.time) && Objects.equals(userId, orderVM.userId) && Objects.equals(totalPrice, orderVM.totalPrice) && Objects.equals(isPaid, orderVM.isPaid) && Objects.equals(tickets, orderVM.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, userId, totalPrice, isPaid, tickets);
    }
}
