package org.app.escqrsaxon.query.entities;

import jakarta.persistence.*;
import lombok.*;
import org.app.escqrsaxon.enums.OperationType;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String currency;
    @ManyToOne
    private Account account;
}