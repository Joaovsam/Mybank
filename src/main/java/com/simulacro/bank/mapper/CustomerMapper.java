package com.simulacro.bank.mapper;

import com.simulacro.bank.DTO.CustomerDTO;
import com.simulacro.bank.model.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void CustomerDtoToCustomer(@MappingTarget Customer customer, CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);

    default Page<CustomerDTO> customersToCustomersDto(Page<Customer> customers) {
        return customers.map(this::customerToCustomerDto);
    }

}
