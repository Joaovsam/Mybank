package com.simulacro.bank.Controller;

import com.simulacro.bank.DTO.CustomerDTO;
import com.simulacro.bank.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@Tag(name = "customers", description = "Database operations for investments")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/pageable")
    @Operation(summary = "Gets all customer", description = "Return all customers with pagination")
    public ResponseEntity<Page<CustomerDTO>> getCustomers(Pageable pageable) {
        return new ResponseEntity<>(customerService.findCustomers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets the customer by ID", description = "Return the customer with the ID in the path")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Create a customer", description = "create a customer with the infomations name, cpf and identityNumber")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer", description = "Update a customer with the infomations name, cpf and "
            + "identityNumber using the id on tha path")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDto, @PathVariable Long id) {
        return new ResponseEntity<>(customerService.updateCustomer(customerDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes the customer by ID", description = "Delete the customer with the ID in the path")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully!");
    }

}
