package com.simulacro.bank.model.repository;

import com.simulacro.bank.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> FindAll(Pageable pageable);
    
}
