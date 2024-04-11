package com.movie.backend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "grab_item", schema = "movieweb", catalog = "")
public class GrabItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "session_id")
    private Integer sessionId;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "row_id")
    private Integer row;
    @Basic
    @Column(name = "col_id")
    private Integer col;

    public GrabItem() {
    }

    public GrabItem(Integer userId, Integer sessionId, Integer status) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer rowId) {
        this.row = rowId;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer colId) {
        this.col = colId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrabItem grabItem = (GrabItem) o;
        return id == grabItem.id && Objects.equals(sessionId, grabItem.sessionId) && Objects.equals(userId, grabItem.userId) && Objects.equals(status, grabItem.status) && Objects.equals(row, grabItem.row) && Objects.equals(col, grabItem.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, userId, status, row, col);
    }
}
