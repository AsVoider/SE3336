package com.movie.backend.dao;

import com.movie.backend.entity.Room;

import java.util.List;

public interface RoomDao {
    List<Room> getRoomsByCinemaId(Integer cinemaId);

    Room getRoomByRoomId(Integer roomId);
}
