package com.movie.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Entity
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "time")
    private Date time;
    @Basic
    @Column(name = "director")
    private String director;
    @Basic
    @Column(name = "actor")
    private String actor;
    @Basic
    @Column(name = "mark")
    private BigDecimal mark;
    @Basic
    @Column(name = "type")
    private Long type;
    @Basic
    @Column(name = "duration")
    private Time duration;
    @Basic
    @Column(name = "src")
    private String src;
    @Basic
    @Column(name = "intro")
    private String intro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && Objects.equals(title, movie.title) && Objects.equals(time, movie.time) && Objects.equals(director, movie.director) && Objects.equals(actor, movie.actor) && Objects.equals(mark, movie.mark) && Objects.equals(type, movie.type) && Objects.equals(duration, movie.duration) && Objects.equals(src, movie.src) && Objects.equals(intro, movie.intro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, time, director, actor, mark, type, duration, src, intro);
    }

    @Transient
    private List<String> types;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
