package com.simulacro.bank.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;
    private Instant data = Instant.now();

    @ManyToOne
    @JoinColumn(name = "originAccountId", nullable = false)
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destinationAccountId", nullable = true)
    private Account destinationAccount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

}
