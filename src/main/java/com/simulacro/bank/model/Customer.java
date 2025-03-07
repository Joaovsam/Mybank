package com.simulacro.bank.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cpf;
    private String identityNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> contas;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<InvestmentCustomer> investmentCustomer;

}
