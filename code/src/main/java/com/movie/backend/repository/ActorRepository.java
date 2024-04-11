package com.movie.backend.repository;

import com.movie.backend.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, String> {
    Actor findActorByNameIs(String name);
}