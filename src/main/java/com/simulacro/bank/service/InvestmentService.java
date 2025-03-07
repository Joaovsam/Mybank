package com.simulacro.bank.service;

import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.model.Investment;
import com.simulacro.bank.model.InvestmentCustomer;
import com.simulacro.bank.model.repository.InvestmentCustomerRepository;
import com.simulacro.bank.model.repository.InvestmentRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final InvestmentCustomerRepository investmentCustomerRepository;

    public Page<Investment> getInvestments(Pageable pageable) {
        return investmentRepository.findAll(pageable);
    }

    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id).orElseThrow(() -> new BankException("Investment not found"));
    }

    public List<InvestmentCustomer> getCustomerInvestments(Long id) {
        return investmentCustomerRepository.findByCustomerId(id);
    }

    @Transactional
    public Investment createInvestment(Investment newInvestment) {
        Investment investment = investmentRepository.save(newInvestment);
        return investment;
    }

    @Transactional
    public Investment updateInvestment(Investment updatedInvestment, Long id) {
        Investment oldInvestment = investmentRepository.findById(id).orElseThrow(() -> new BankException("Investment not found"));

        Arrays.stream(updatedInvestment.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object newValue = field.get(updatedInvestment);
                        if (Objects.nonNull(newValue)) {
                            field.set(oldInvestment, newValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao atualizar campos", e);
                    }
                });

        return investmentRepository.save(updatedInvestment);
    }

    @Transactional
    public void deleteInvestment(Long id) {
        Investment toDeletInvestment = investmentRepository.findById(id).orElseThrow(() -> new BankException("Investment not found"));
        investmentRepository.delete(toDeletInvestment);
    }

}
