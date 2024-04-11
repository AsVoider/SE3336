package com.movie.backend.dto;

import com.movie.backend.entity.Actor;
import com.movie.backend.entity.Director;
import com.movie.backend.entity.Movie;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MovieDTO {
    private int id;
    private String title;
    private Date time;
    private List<Director> directors;
    private List<Actor> actors;
    private BigDecimal mark;
    private List<String> types;
    private Time duration;
    private String src;
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

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
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

    public void parseTypes(Movie movie) {
        String[] movieTypes = {"科幻", "冒险", "悬疑", "喜剧", "动作", "爱情", "动画"};
        if (movie == null) return;
        Long type = movie.getType();
        List<String> types = new ArrayList<>();
        int num = 0;
        while (type != 0) {
            if (type % 2 != 0) {
                types.add(movieTypes[num]);
            }
            type /= 2;
            num++;
        }
        setTypes(types);
    }

//    public void transMovieIntoDTO(Movie movie) {
//        if (movie == null) return;
//        setId(movie.getId());
//        setTitle(movie.getTitle());
//        setTime(movie.getTime());
//        String[] directors = movie.getDirector().split(" ");
//        List<String> directorsList = new ArrayList<>();
//        for (String director : directors) {
//            directorsList.add(director);
//        }
//        setDirectors(directorsList);
//        String[] actors = movie.getActor().split(" ");
//        List<String> actorsList = new ArrayList<>();
//        for (String actor : actors) {
//            actorsList.add(actor);
//        }
//        setActors(actorsList);
//        setMark(movie.getMark());
//        parseTypes(movie);
//        setDuration(movie.getDuration());
//        setSrc(movie.getSrc());
//        setIntro(movie.getIntro());
//    }
}
