package com.simulacro.bank.service;

import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.repository.AccountRepository;
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
public class AccountService {

    private final AccountRepository accountRepository;

    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.FindAll(pageable);
    }

    public Account getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new BankException("Account not found!"));
        return account;
    }

    public List<Account> getAccountFromCustomer(Long id) {
        List<Account> account = accountRepository.findByClienteId(id);
        if (account.isEmpty()) {
            throw new BankException("Customer doesn't have accounts");
        }
        return account;
    }

    @Transactional
    public Account createAccount(Account newAccount) {
        Account account = accountRepository.save(newAccount);
        return account;
    }

    @Transactional
    public Account updateAccount(Account updatedAccount, Long id) {
        Account oldAccount = accountRepository.findById(id).orElseThrow(() -> new BankException("Account not Found"));

        //Colocar mapstruct
        Arrays.stream(updatedAccount.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object newValue = field.get(updatedAccount);
                        if (Objects.nonNull(newValue)) {
                            field.set(oldAccount, newValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao atualizar campos", e);
                    }
                });

        return accountRepository.save(oldAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account oldAccount = accountRepository.findById(id).orElseThrow(() -> new BankException("Account not Found"));
        accountRepository.delete(oldAccount);
    }
}
