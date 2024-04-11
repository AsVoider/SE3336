package com.movie.backend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Director {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "img")
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(name, director.name) && Objects.equals(img, director.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, img);
    }
}
