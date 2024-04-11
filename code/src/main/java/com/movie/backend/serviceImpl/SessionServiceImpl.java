package com.movie.backend.serviceImpl;

import com.movie.backend.dao.RoomDao;
import com.movie.backend.dao.SessionDao;
import com.movie.backend.entity.Room;
import com.movie.backend.entity.Session;
import com.movie.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    RoomDao roomDao;
    @Autowired
    SessionDao sessionDao;

    private void parseSeats(Session session) {
        if (session == null) return;
        Room room = session.getRoom();
        Integer row = room.getRow();
        Integer col = room.getCol();

        String seatStr = session.getSeat();

        assert (row * col <= seatStr.length());

        //-1表示已被购买 0表示可选 1表示在支付中状态
        List<List<Integer>> seats = new ArrayList<>();

        for (int i = 0; i < row; ++i) {
            List<Integer> seatRow = new ArrayList<>();
            for (int j = 0; j < col; ++j) {
                int index = i * col + j;
                int state = seatStr.charAt(index) - '1';
                seatRow.add(state);
            }
            seats.add(seatRow);
        }

        session.setSeats(seats);
    }
    @Override
    public List<Session> getSessionsByMovieAndCinema(Integer movieId, Integer cinemaId) {
        List<Session> sessions = new ArrayList<>();
        if (movieId == null || cinemaId == null) {
            return sessions;
        }

        //先查找放映厅
        List<Room> rooms = roomDao.getRoomsByCinemaId(cinemaId);

        //根据电影和放映厅，依次进行查找
        for (int i = 0; i < rooms.size(); ++i) {
            List<Session> roomSessions = sessionDao.getSessionByMovieAndRoom(movieId, rooms.get(i).getId());

            //对座位信息进行处理
            if (roomSessions != null) {
                for (int j = 0; j < roomSessions.size(); ++j) {
                    Session session = roomSessions.get(j);
                    parseSeats(session);
                    sessions.add(session);
                }
            }
        }
        return sessions;
    }

    @Override
    public Session getSessionById(Integer sessionId) {
        Optional<Session> session = sessionDao.getSessionById(sessionId);
        if (session.isPresent()) {
            parseSeats(session.get());
            return session.get();
        } else {
            return null;
        }
    }

    @Override
    public void updateSession(Map<String, String> params) {
        Integer sessionId = Integer.parseInt(params.get("id"));
        Session session = getSessionById(sessionId);
        BigDecimal price = new BigDecimal(params.get("price"));
        //System.out.println(price);
        session.setPrice(price);
        sessionDao.saveSession(session);
    }
}
