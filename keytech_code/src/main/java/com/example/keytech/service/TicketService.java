package com.example.keytech.service;

import com.example.keytech.entity.Ticket;

public interface TicketService {
    Ticket getOneTicket(Long sessionId);

    String getSeats(Long sessionId);
}
