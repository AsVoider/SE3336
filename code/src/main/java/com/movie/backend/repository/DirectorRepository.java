package com.movie.backend.repository;

import com.movie.backend.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, String> {
    Director findDirectorByNameIs(String name);
}