package com.movie.backend.repository;

import com.movie.backend.entity.GrabItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrabItemRepository extends JpaRepository<GrabItem, Integer> {
    List<GrabItem> findAllByUserId(Integer userId);
}
