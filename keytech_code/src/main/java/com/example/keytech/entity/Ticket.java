package com.example.keytech.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    private Long sessionId;

    @Column(name = "row_number")
    private Integer row;

    @Column(name = "col_number")
    private Integer col;

    public Ticket(Long sessionId, Integer row, Integer col) {
        this.sessionId = sessionId;
        this.row = row;
        this.col = col;
    }
}
