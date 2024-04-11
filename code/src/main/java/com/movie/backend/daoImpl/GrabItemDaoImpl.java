package com.movie.backend.daoImpl;

import com.movie.backend.dao.GrabItemDao;
import com.movie.backend.entity.GrabItem;
import com.movie.backend.repository.GrabItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GrabItemDaoImpl implements GrabItemDao {

    @Autowired
    GrabItemRepository grabItemRepository;

    @Override
    public List<GrabItem> findAllByUserId(Integer userId) {
        return grabItemRepository.findAllByUserId(userId);
    }

    @Override
    public GrabItem save(GrabItem grabItem) {
        return grabItemRepository.save(grabItem);
    }
}
