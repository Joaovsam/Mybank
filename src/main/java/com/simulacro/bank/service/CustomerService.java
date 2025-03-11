package com.simulacro.bank.service;

import com.simulacro.bank.DTO.CustomerDTO;
import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.mapper.CustomerMapper;
import com.simulacro.bank.model.Customer;
import com.simulacro.bank.repository.CustomerRepository;
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
    private final CustomerMapper customerMapper;

    public Page<CustomerDTO> findCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        Page<CustomerDTO> customersDto = customerMapper.customersToCustomersDto(customers);
        return customersDto;
    }

    public CustomerDTO findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new BankException("Customer not Found"));
        return customerMapper.customerToCustomerDto(customer);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDto) {
        Customer newCustomer = new Customer();
        customerMapper.CustomerDtoToCustomer(newCustomer, customerDto);
        newCustomer = customerRepository.save(newCustomer);
        return customerMapper.customerToCustomerDto(newCustomer);
    }

    @Transactional
    public CustomerDTO updateCustomer(CustomerDTO customerDto, Long id) {
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(() -> new BankException("Customer not Found"));
        customerMapper.CustomerDtoToCustomer(oldCustomer, customerDto);
        Customer updatedCustomer = customerRepository.save(oldCustomer);
        return customerMapper.customerToCustomerDto(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new BankException("Customer not Found"));
        customerRepository.delete(customer);
    }
}
