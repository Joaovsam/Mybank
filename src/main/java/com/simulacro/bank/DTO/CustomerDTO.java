package com.simulacro.bank.DTO;

import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.InvestmentCustomer;
import java.util.List;
import lombok.Data;

@Data
public class CustomerDTO {

    private String name;
    private String cpf;
    private String identityNumber;
    private List<Account> contas;
    private List<InvestmentCustomer> investmentCustomer;

}
