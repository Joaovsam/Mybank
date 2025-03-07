package com.simulacro.bank.repository;

import com.simulacro.bank.model.InvestmentCustomer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentCustomerRepository extends JpaRepository<InvestmentCustomer, Long> {

    List<InvestmentCustomer> findByCustomerId(Long customerId);
    
}
