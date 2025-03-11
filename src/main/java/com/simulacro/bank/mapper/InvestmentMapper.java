package com.simulacro.bank.mapper;

import com.simulacro.bank.DTO.InvestmentDTO;
import com.simulacro.bank.model.Investment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "investmentsCustomers", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void investmentDtoToInvestment(@MappingTarget Investment investment, InvestmentDTO investmentDTO);

    InvestmentDTO investmentToInvestmentDto(Investment investment);

    default Page<InvestmentDTO> investmentsToInvestmentsDto(Page<Investment> investments) {
        return investments.map(this::investmentToInvestmentDto);
    }
}
