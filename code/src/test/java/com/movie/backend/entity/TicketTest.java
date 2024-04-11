package com.movie.backend.entity;

import com.movie.backend.BackendApplication;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class TicketTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("ticket test all")
    public void testAll() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        ticket1.setId(1);
        ticket2.setId(ticket1.getId());
        Assertions.assertEquals(true, ticket1.equals(ticket2));

        Session session = new Session();
        ticket1.setSession(session);
        ticket1.setState(1);
        ticket1.setCol(1);
        ticket1.setRow(1);
        ticket1.setSessionId(1);
        ticket1.setOrderId(1);
        ticket1.setPrice(BigDecimal.ZERO);
        int hash = ticket1.hashCode();
    }
}