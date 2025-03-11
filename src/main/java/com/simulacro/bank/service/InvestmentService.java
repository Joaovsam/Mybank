package com.simulacro.bank.service;

import com.simulacro.bank.DTO.InvestmentDTO;
import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.mapper.InvestmentMapper;
import com.simulacro.bank.model.Investment;
import com.simulacro.bank.model.InvestmentCustomer;
import com.simulacro.bank.repository.InvestmentCustomerRepository;
import com.simulacro.bank.repository.InvestmentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final InvestmentCustomerRepository investmentCustomerRepository;
    private final InvestmentMapper investmentMapper;

    public Page<InvestmentDTO> getAllInvestments(Pageable pageable) {
        return investmentMapper.investmentsToInvestmentsDto(investmentRepository.findAll(pageable));
    }

    public InvestmentDTO getInvestmentById(Long id) {
        Investment investment = investmentRepository.findById(id).orElseThrow(() -> new BankException("Investment not found"));
        return investmentMapper.investmentToInvestmentDto(investment);
    }

    public List<InvestmentCustomer> getCustomerInvestments(Long id) {
        return investmentCustomerRepository.findByCustomerId(id);
    }

    @Transactional
    public InvestmentDTO createInvestment(InvestmentDTO investmentDTO) {
        Investment newInvestment = new Investment();
        investmentMapper.investmentDtoToInvestment(newInvestment, investmentDTO);
        newInvestment = investmentRepository.save(newInvestment);
        return investmentMapper.investmentToInvestmentDto(newInvestment);
    }

    @Transactional
    public InvestmentDTO updateInvestment(InvestmentDTO investmentDto, Long id) {
        Investment oldInvestment = investmentRepository.findById(id).orElseThrow(() -> new BankException("Investment not found"));

        investmentMapper.investmentDtoToInvestment(oldInvestment, investmentDto);
        oldInvestment = investmentRepository.save(oldInvestment);

        return investmentMapper.investmentToInvestmentDto(oldInvestment);
    }

    @Transactional
    public void deleteInvestment(Long id) {
        Investment toDeletInvestment = investmentRepository.findById(id).orElseThrow(() -> new BankException("Investment not found"));
        investmentRepository.delete(toDeletInvestment);
    }

}
