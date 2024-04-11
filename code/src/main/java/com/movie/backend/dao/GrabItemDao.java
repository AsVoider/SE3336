package com.movie.backend.dao;

import com.movie.backend.entity.GrabItem;

import java.util.List;

public interface GrabItemDao {
    List<GrabItem> findAllByUserId(Integer userId);

    GrabItem save(GrabItem grabItem);
}
