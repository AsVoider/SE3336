package com.movie.backend.daoImpl;

import com.movie.backend.dao.ActorDao;
import com.movie.backend.entity.Actor;
import com.movie.backend.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActorDaoImpl implements ActorDao {
    @Autowired
    ActorRepository actorRepository;
    @Override
    public Actor getActorByName(String name) {
        return actorRepository.findActorByNameIs(name);
    }
}
