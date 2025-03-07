package com.simulacro.bank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "bankTransactions")
public class BankTransaction extends Transaction {

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "originAccountId", nullable = false)
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destinationAccountId", nullable = true)
    private Account destinationAccount;
}
