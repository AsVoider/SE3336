package com.movie.backend.daoImpl;

import com.movie.backend.dao.DirectorDao;
import com.movie.backend.entity.Director;
import com.movie.backend.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DirectorDaoImpl implements DirectorDao {
    @Autowired
    DirectorRepository directorRepository;

    @Override
    public Director getDirectorByName(String name) {
        return directorRepository.findDirectorByNameIs(name);
    }
}
