package com.simulacro.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numberAccount;
    private int agency;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "originAccount", cascade = CascadeType.ALL)
    private List<BankTransaction> sendTransactions;

    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL)
    private List<BankTransaction> receivedTransactions;

}
