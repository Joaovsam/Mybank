package com.simulacro.bank.service;

import com.simulacro.bank.DTO.AccountDTO;
import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.mapper.AccountMapper;
import com.simulacro.bank.model.Account;
import com.simulacro.bank.repository.AccountRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public Page<AccountDTO> getAllAccounts(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accountMapper.accounsToAccountDto(accounts);
    }

    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new BankException("Account not found!"));
        return accountMapper.accountToAccountDto(account);
    }

    public List<AccountDTO> getAccountFromCustomer(Long id) {
        List<Account> accounts = accountRepository.findByCustomerId(id);
        if (accounts.isEmpty()) {
            throw new BankException("Customer doesn't have accounts");
        }
        return accountMapper.accountListToAccountDtoList(accounts);
    }

    @Transactional
    public AccountDTO createAccount(Account newAccount) {
        Account account = accountRepository.save(newAccount);
        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    public AccountDTO updateAccount(AccountDTO accountDto, Long id) {
        Account oldAccount = accountRepository.findById(id).orElseThrow(() -> new BankException("Account not Found"));

        accountMapper.accountDtoToAccount(accountDto, oldAccount);
        oldAccount = accountRepository.save(oldAccount);

        return accountMapper.accountToAccountDto(oldAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account oldAccount = accountRepository.findById(id).orElseThrow(() -> new BankException("Account not Found"));
        accountRepository.delete(oldAccount);
    }
}
