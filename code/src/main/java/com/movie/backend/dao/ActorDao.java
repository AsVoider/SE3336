package com.movie.backend.dao;

import com.movie.backend.entity.Actor;

public interface ActorDao {
    Actor getActorByName(String name);
}
