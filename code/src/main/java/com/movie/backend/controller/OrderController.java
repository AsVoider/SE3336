package com.movie.backend.controller;

import com.movie.backend.dao.*;
import com.movie.backend.dto.OrderVM;
import com.movie.backend.dto.SeatsDTO;
import com.movie.backend.entity.*;
import com.movie.backend.repository.TicketRepository;
import com.movie.backend.service.OrderService;
import com.movie.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    SessionDao sessionDao;

    @Autowired
    CinemaDao cinemaDao;

    @Autowired
    MovieDao movieDao;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private GrabItemDao grabItemDao;

    @RequestMapping("/getOrders/{userId}")
    List<OrderVM> getOrdersByUserId(@PathVariable Integer userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @RequestMapping("/getOrder/{id}")
    OrderVM getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @RequestMapping("/buyTickets")
    Integer buyTickets(@RequestBody SeatsDTO seatsDTO) {
        if (seatsDTO == null) {
            return -1;
        } else {
            return orderService.addOrder(seatsDTO.getUserId(),
                    seatsDTO.getSessionId(), seatsDTO.getSeats());
        }
    }

    @RequestMapping("/deleteOrder/{orderId}")
    void deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
    }
    
    @RequestMapping("/grabTicket/{userId}/{sessionId}")
    ResponseEntity<?> grabTicket(@PathVariable Integer userId,
                                 @PathVariable Integer sessionId) {
        orderService.grabTicket(userId, sessionId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/getGrabTickets/{userId}")
    List<Map<String, Object>> getGrabTickets(@PathVariable Integer userId) {
        List<GrabItem> grabItems = grabItemDao.findAllByUserId(userId);
        List<Map<String, Object>> ret = new ArrayList<>();
        for (GrabItem grabItem : grabItems) {
            Integer sessionId = grabItem.getSessionId();
            Session session = sessionDao.getSessionById(sessionId).orElse(null);
            if (session == null) continue;
            Integer movieId = session.getMovieId();
            Movie movie = movieDao.getMovie(movieId);
            if (movie == null) continue;
            Integer cinemaId = session.getRoom().getCinemaId();
            Cinema cinema = cinemaDao.getCinemaById(cinemaId);
            if (cinema == null) continue;
            String status;
            if (grabItem.getStatus() == 0) {
                status = "正在抢票";
            } else if (grabItem.getStatus() == 1) {
                status = "抢票成功";
            } else {
                status = "抢票失败";
            }
            Map<String, Object> map = Map.of(
                    "id", grabItem.getId(),
                    "movieName", movie.getTitle(),
                    "cinemaName", cinema.getName(),
                    "status", status
            );
            ret.add(map);
        }
        return ret;
    }

}
