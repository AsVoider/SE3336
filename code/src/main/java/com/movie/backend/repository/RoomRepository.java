package com.movie.backend.repository;

import com.movie.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findRoomsByCinemaIdIs(Integer cinemaId);

    Room findRoomByIdIs(Integer roomId);
}