package com.simulacro.bank.DTO;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionDTO {

    private BigDecimal value;
    private Long originAccount;
    private Long destinationAccount;

}
