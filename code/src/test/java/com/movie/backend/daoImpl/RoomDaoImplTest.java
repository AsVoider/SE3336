package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.RoomDao;
import com.movie.backend.entity.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class RoomDaoImplTest {
    @Autowired
    RoomDao roomDao;

    @Test
    @DisplayName("get room by cinema id and check")
    void getRoomsByCinemaId() {
        Integer id = 1;
        List<Room> rooms = roomDao.getRoomsByCinemaId(id);
        Assertions.assertNotNull(rooms);
        if (!rooms.isEmpty()) {
            Assertions.assertEquals(rooms.get(0).getCinemaId(), id);
        }
        if (rooms.size() > 1) {
            Assertions.assertEquals(rooms.get(0).getCinemaId(), rooms.get(1).getCinemaId());
        }
    }

    @Test
    @DisplayName("Get Illegal Id Room")
    void GetIllegalIdRoom() {
        Integer cinemaId = 999; // Assuming this cinema ID does not exist

        // When
        List<Room> rooms = roomDao.getRoomsByCinemaId(cinemaId);

        // Then
        assertNotNull(rooms);
        assertTrue(rooms.isEmpty());
    }

    @Test
    @DisplayName("Get Null Cinema Id")
    void GetNullCinemaId() {
        Integer cinemaId = null;
        List<Room> roomList = null;
        try {
            roomList = roomDao.getRoomsByCinemaId(cinemaId);
        } catch (Exception e) {
            assert (roomList.isEmpty());
        }
        assert (roomList.isEmpty());
    }

    @Test
    @DisplayName("Get Room By Id")
    void getRoomByRoomId() {
        Room room = roomDao.getRoomByRoomId(1);
        Room room1 = roomDao.getRoomByRoomId(1);
        Assertions.assertEquals(true, room.equals(room1));
        Assertions.assertNotNull(room);
        Assertions.assertEquals(room.getRow(), 8);
    }

    @Test
    @DisplayName("Get Room By Illegal Id")
    void getRoomByIllegalId() {
        Integer roomId = 999; // Assuming this room ID does not exist
        // When
        var room = roomDao.getRoomByRoomId(roomId);
        // Then
        assertNull(room);

        var room1 = roomDao.getRoomByRoomId(null);

        assertNull(room1);
    }
}