package com.movie.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "session_id")
    private Integer sessionId;
    @Basic
    @Column(name = "row_id")
    private Integer row;
    @Basic
    @Column(name = "col_id")
    private Integer col;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "state")
    private Integer state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Objects.equals(orderId, ticket.orderId) && Objects.equals(sessionId, ticket.sessionId) && Objects.equals(row, ticket.row) && Objects.equals(col, ticket.col) && Objects.equals(price, ticket.price) && Objects.equals(state, ticket.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, sessionId, row, col, price, state);
    }

    @ManyToOne
    @JoinColumn(name = "session_id", insertable=false, updatable=false)
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
