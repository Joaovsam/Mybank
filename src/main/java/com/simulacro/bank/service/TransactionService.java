package com.simulacro.bank.service;

import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.Transaction;
import com.simulacro.bank.model.TransactionType;
import com.simulacro.bank.model.repository.AccountRepository;
import com.simulacro.bank.model.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal value) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Account not Found"));

        account.setBalance(account.getBalance().add(value));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setDestinationAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setValue(value);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal value) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Account not Found"));

        account.setBalance(account.getBalance().subtract(value));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setOriginAccount(account);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setValue(value);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(Long originAccountId, Long destinationAccountId, BigDecimal value) {
        Account originAccount = accountRepository.findById(originAccountId).orElseThrow(() -> new BankException("Origin account not Found"));
        Account destinationAccount = accountRepository.findById(destinationAccountId).orElseThrow(() -> new BankException("Destination account not Found"));

        originAccount.setBalance(originAccount.getBalance().subtract(value));
        accountRepository.save(originAccount);

        destinationAccount.setBalance(destinationAccount.getBalance().add(value));
        accountRepository.save(destinationAccount);

        Transaction transaction = new Transaction();
        transaction.setDestinationAccount(destinationAccount);
        transaction.setOriginAccount(originAccount);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setValue(value);
        return transactionRepository.save(transaction);
    }

    public Page<Transaction> getAllAccountTransactions(Long accountId, Pageable pageable) {
        return transactionRepository.findByContaId(accountId, pageable);
    }

}
