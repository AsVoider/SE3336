package com.movie.backend.serviceImpl;

import com.movie.backend.dao.CinemaDao;
import com.movie.backend.dao.RoomDao;
import com.movie.backend.dao.SessionDao;
import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.Room;
import com.movie.backend.entity.Session;
import com.movie.backend.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaDao cinemaDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    SessionDao sessionDao;

    @Override
    public List<Cinema> getAllCinemas() {
        return cinemaDao.getAllCinemas();
    }

    @Override
    public List<Cinema> getCinemasByMovieId(Integer movieId) {
        //非法输入
        if (movieId == null) {
            return new ArrayList<>();
        }
        //movie -> session -> room -> cinema 记录最小价格的过程
        //通过场次查找放映厅，并记录每个放映厅的最低价格
        //roomIds记录放映厅号，set保证其值唯一
        List<Session> sessions = sessionDao.getSessionsByMovie(movieId);
        Map<Integer, BigDecimal> roomMinPrices = new HashMap<>();
        Set<Integer> roomIds = new HashSet<>();
        for (Session session : sessions) {
            Integer roomId = session.getRoomId();
            roomIds.add(roomId);
            if (roomMinPrices.containsKey(roomId)) {
                BigDecimal oldMinPrice = roomMinPrices.get(roomId);
                BigDecimal curMinPrice = session.getPrice();
//                if (curMinPrice.compareTo(oldMinPrice) < 0) {
//                    roomMinPrices.put(roomId, curMinPrice);
//                }
            } else {
                roomMinPrices.put(roomId, session.getPrice());
            }
        }

        //通过放映厅查找电影院
        //cinemaIds记录电影院，且其值唯一；map记录最低价
        Map<Integer, BigDecimal> cinemaMinPrices = new HashMap<>();
        Set<Integer> cinemaIds = new HashSet<>();
        for (Integer roomId : roomIds) {
            Room room = roomDao.getRoomByRoomId(roomId);
            Integer cinemaId = room.getCinemaId();
            cinemaIds.add(cinemaId);
            if (cinemaMinPrices.containsKey(cinemaId)) {
                BigDecimal oldMinPrice = cinemaMinPrices.get(cinemaId);
                BigDecimal curMinPrice = roomMinPrices.get(roomId);
//                if (curMinPrice.compareTo(oldMinPrice) < 0) {
//                    cinemaMinPrices.put(cinemaId, curMinPrice);
//                }
            } else {
                cinemaMinPrices.put(cinemaId, roomMinPrices.get(roomId));
            }
        }

        //转换成cinemas
        List<Cinema> cinemas = new ArrayList<>();
        for (Integer cinemaId : cinemaIds) {
            Cinema cinema = cinemaDao.getCinemaById(cinemaId);
            cinema.setMinPrice(cinemaMinPrices.get(cinemaId));
            cinemas.add(cinema);
        }
        return cinemas;
    }

    @Override
    public Cinema getCinemaById(Integer cinemaId) {
        return cinemaDao.getCinemaById(cinemaId);
    }

    @Override
    public void updateCinema(Map<String, String> params) {
        Integer cinemaId = Integer.parseInt(params.get("id"));
        Cinema cinema = cinemaDao.getCinemaById(cinemaId);
        String name = params.get("name");
        cinema.setName(name);
        String address = params.get("address");
        cinema.setAddress(address);
        cinemaDao.save(cinema);
    }
}
