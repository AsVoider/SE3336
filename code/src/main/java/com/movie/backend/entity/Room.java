package com.movie.backend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "cinema_id")
    private int cinemaId;
    @Basic
    @Column(name = "row_num")
    private Integer row;
    @Basic
    @Column(name = "col_num")
    private Integer col;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && cinemaId == room.cinemaId && Objects.equals(name, room.name) && Objects.equals(row, room.row) && Objects.equals(col, room.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cinemaId, row, col);
    }

    @ManyToOne
    @JoinColumn(name = "cinema_id", insertable=false, updatable=false)
    private Cinema cinema;

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}
