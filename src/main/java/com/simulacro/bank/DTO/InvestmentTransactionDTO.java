package com.simulacro.bank.DTO;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class InvestmentTransactionDTO {

    private Long investmentId;
    private Long accountId;
    private Long investmentCustomerId;
    private BigDecimal quantity;
    private BigDecimal aplicationPrice;
    private BigDecimal transactionTax;
}
