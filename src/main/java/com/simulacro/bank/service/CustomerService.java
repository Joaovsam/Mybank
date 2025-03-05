package com.simulacro.bank.service;

import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.model.Customer;
import com.simulacro.bank.model.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Page<Customer> findCustomers(Pageable pageable) {
        Page<Customer> customer = customerRepository.findAll(pageable);
        return customer;
    }

    public Customer findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new BankException("Customer not Found"));
        return customer;
    }

    @Transactional
    public Customer createCustomer(Customer newCustomer) {
        Customer customer = customerRepository.save(newCustomer);
        return customer;
    }

    @Transactional
    public Customer updateCustomer(Customer updatedCustomer, Long id) {
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(() -> new BankException("Customer not Found"));

        //Colocar mapstruct
        Arrays.stream(updatedCustomer.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object newValue = field.get(updatedCustomer);
                        if (Objects.nonNull(newValue)) {
                            field.set(oldCustomer, newValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao atualizar campos", e);
                    }
                });

        return customerRepository.save(oldCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new BankException("Customer not Found"));
        customerRepository.delete(customer);
    }
}
