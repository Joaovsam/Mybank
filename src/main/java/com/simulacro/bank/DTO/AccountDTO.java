package com.simulacro.bank.DTO;

import com.simulacro.bank.model.AccountType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDTO {

    private Long id;
    private Long numberAccount;
    private int agency;
    private BigDecimal balance;
    private double yieldRate;
    private double creditLimit;
    private AccountType accountType;
    private Long customerId;

}
