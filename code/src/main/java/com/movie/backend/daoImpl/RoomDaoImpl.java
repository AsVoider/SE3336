package com.movie.backend.daoImpl;

import com.movie.backend.dao.RoomDao;
import com.movie.backend.entity.Room;
import com.movie.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDaoImpl implements RoomDao {
    @Autowired
    RoomRepository roomRepository;
    @Override
    public List<Room> getRoomsByCinemaId(Integer cinemaId) {
        return roomRepository.findRoomsByCinemaIdIs(cinemaId);
    }

    @Override
    public Room getRoomByRoomId(Integer roomId) {
        return roomRepository.findRoomByIdIs(roomId);
    }
}
