package com.example.keytech.service.impl;

import com.example.keytech.entity.Session;
import com.example.keytech.entity.Ticket;
import com.example.keytech.repository.SessionRepository;
import com.example.keytech.repository.TicketRepository;
import com.example.keytech.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public Ticket getOneTicket(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException(String.format("Session %d not found", sessionId)));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String seats = session.getSeats();
        int cols = session.getCols();
        int rows = session.getRows();

        StringBuilder sb = new StringBuilder(seats);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int index = i * cols + j;
                if (sb.charAt(index) == '1') {
                    sb.setCharAt(index, '0');
                    session.setSeats(sb.toString());
                    sessionRepository.save(session);
                    return ticketRepository.save(new Ticket(sessionId, i + 1, j + 1));
                }
            }
        }

        return null;
    }

    @Override
    public String getSeats(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException(String.format("Session %d not found", sessionId)));
        return session.getSeats();
    }
}
