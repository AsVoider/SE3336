package com.movie.backend.daoImpl;

import com.movie.backend.dao.TicketDao;
import com.movie.backend.entity.Ticket;
import com.movie.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDaoImpl implements TicketDao {
    @Autowired
    TicketRepository ticketRepository;
    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
