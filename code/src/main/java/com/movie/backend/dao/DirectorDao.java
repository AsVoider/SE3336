package com.movie.backend.dao;

import com.movie.backend.entity.Director;

public interface DirectorDao {
    Director getDirectorByName(String name);
}
