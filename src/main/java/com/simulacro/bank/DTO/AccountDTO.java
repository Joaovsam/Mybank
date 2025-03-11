package com.simulacro.bank.DTO;

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
    private String accountType;

}
