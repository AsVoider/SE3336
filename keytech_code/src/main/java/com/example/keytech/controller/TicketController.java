package com.example.keytech.controller;

import com.example.keytech.entity.Ticket;
import com.example.keytech.service.TicketService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TicketController {

    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    TicketService ticketService;

    @GetMapping("/ping")
    ResponseEntity<?> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/seats/{sessionId}")
    ResponseEntity<?> seats(@PathVariable Long sessionId) {
        return ResponseEntity.ok(ticketService.getSeats(sessionId));
    }

    @GetMapping("/buy/{sessionId}")
    ResponseEntity<?> buy(@PathVariable Long sessionId) throws Exception {
//        with lock
        InterProcessMutex mtx = new InterProcessMutex(curatorFramework, "/session_" + sessionId);
        Ticket ticket = null;
        try {
            mtx.acquire();
            ticket = ticketService.getOneTicket(sessionId);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw e;
            }
        } finally {
            mtx.release();
        }
//         without lock
//        Ticket ticket = ticketService.getOneTicket(sessionId);
        return ResponseEntity.ok(Objects.requireNonNullElse(ticket, "no ticket available"));
    }
}
