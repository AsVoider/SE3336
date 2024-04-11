package com.movie.backend.repository;

import com.movie.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}