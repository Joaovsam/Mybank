package com.simulacro.bank.DTO;

import com.simulacro.bank.model.InvestmentType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class InvestmentDTO {

    private Long id;
    private String nome;
    private InvestmentType type;
    private BigDecimal rentability;
    private BigDecimal unitPrice;

}
