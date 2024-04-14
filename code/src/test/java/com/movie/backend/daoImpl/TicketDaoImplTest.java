package com.movie.backend.daoImpl;

import com.movie.backend.BackendApplication;
import com.movie.backend.dao.TicketDao;
import com.movie.backend.entity.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class TicketDaoImplTest {
    @Autowired
    TicketDao ticketDao;

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Tickets")
    void saveTicket() {
        Ticket ticket = new Ticket();
        Integer state = 1111;
        ticket.setState(state);
        Ticket ticket1 = ticketDao.saveTicket(ticket);
        Assertions.assertNotEquals(0, ticket1.getId());
        Assertions.assertEquals(state, ticket1.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("Save Invalid Tickets")
    void saveInvalidTickets() {
        Ticket ticket = null;
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            ticketDao.saveTicket(ticket);
        });
    }
}