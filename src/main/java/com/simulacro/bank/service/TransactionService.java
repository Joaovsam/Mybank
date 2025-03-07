package com.simulacro.bank.service;

import com.simulacro.bank.handler.BankException;
import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.BankTransaction;
import com.simulacro.bank.model.Customer;
import com.simulacro.bank.model.Investment;
import com.simulacro.bank.model.InvestmentCustomer;
import com.simulacro.bank.model.Transaction;
import com.simulacro.bank.model.InvestmentTransaction;
import com.simulacro.bank.model.TransactionInvestmentType;
import com.simulacro.bank.model.TransactionType;
import com.simulacro.bank.repository.AccountRepository;
import com.simulacro.bank.repository.InvestmentCustomerRepository;
import com.simulacro.bank.repository.InvestmentRepository;
import com.simulacro.bank.repository.TransactionRepository;
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
    private final InvestmentCustomerRepository investmentCustomerRepository;
    private final InvestmentRepository investmentRepository;

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal value) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Account not Found"));

        account.setBalance(account.getBalance().add(value));
        accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setDestinationAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setValue(value);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal transferValue) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Account not Found"));

        BigDecimal accountBalance = account.getBalance().subtract(transferValue);
        if (accountBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankException("Not enough money in account: " + account.getNumberAccount());
        }

        account.setBalance(account.getBalance().subtract(transferValue));
        accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setOriginAccount(account);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setValue(transferValue);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(Long originAccountId, Long destinationAccountId, BigDecimal transferValue) {
        Account originAccount = accountRepository.findById(originAccountId).orElseThrow(() -> new BankException("Origin account not Found"));
        Account destinationAccount = accountRepository.findById(destinationAccountId).orElseThrow(() -> new BankException("Destination account not Found"));

        BigDecimal accountBalance = originAccount.getBalance().subtract(transferValue);
        if (accountBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankException("Not enough money in account: " + originAccount.getNumberAccount());
        }

        originAccount.setBalance(originAccount.getBalance().subtract(transferValue));
        accountRepository.save(originAccount);

        destinationAccount.setBalance(destinationAccount.getBalance().add(transferValue));
        accountRepository.save(destinationAccount);

        BankTransaction transaction = new BankTransaction();
        transaction.setDestinationAccount(destinationAccount);
        transaction.setOriginAccount(originAccount);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setValue(transferValue);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction buyInvestment(Long investmentId, Long accountId, BigDecimal aplicationPrice, BigDecimal transactionTax) {
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(() -> new BankException("Investment not Found"));
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Account not Found"));
        Customer customer = account.getCustomer();

        BigDecimal accountBalance = account.getBalance().subtract(aplicationPrice);
        if (accountBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankException("Not enough money in account: " + account.getNumberAccount());
        }

        account.setBalance(account.getBalance().subtract(aplicationPrice));
        accountRepository.save(account);

        BigDecimal finalValue = aplicationPrice.subtract(transactionTax);
        BigDecimal quantity = finalValue.divide(investment.getUnitPrice());

        InvestmentCustomer investmentCustomer = new InvestmentCustomer();
        investmentCustomer.setInvestment(investment);
        investmentCustomer.setCustomer(customer);
        investmentCustomer.setTotalValue(finalValue);
        investmentCustomer.setQuantity(quantity);

        investmentCustomer = investmentCustomerRepository.save(investmentCustomer);

        InvestmentTransaction investmentTransaction = new InvestmentTransaction();
        investmentTransaction.setInvestmentCustomer(investmentCustomer);
        investmentTransaction.setType(TransactionInvestmentType.BUY);
        investmentTransaction.setQuantity(quantity);
        investmentTransaction.setValue(aplicationPrice);

        return transactionRepository.save(investmentTransaction);
    }

    @Transactional
    public Transaction sellInvestment(Long investmentCustomerId, Long accountId, BigDecimal quantity, BigDecimal transactionTax) {
        InvestmentCustomer investmentCustomer = investmentCustomerRepository.findById(investmentCustomerId).orElseThrow(() -> new BankException("Investment not Found"));
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Account not Found"));
        Investment investment = investmentCustomer.getInvestment();
        Customer customer = investmentCustomer.getCustomer();

        BigDecimal investmentQuantity = investmentCustomer.getQuantity().subtract(quantity);
        if (investmentQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankException("Not enough currency: " + investmentCustomer.getInvestment().getNome());
        }

        BigDecimal transactionValue = quantity.multiply(investment.getUnitPrice());
        BigDecimal toReceiveValue = transactionValue.subtract(transactionTax);
        BigDecimal investmentValue = investmentCustomer.getTotalValue().subtract(transactionValue);

        account.setBalance(account.getBalance().add(toReceiveValue));
        accountRepository.save(account);

        investmentCustomer.setInvestment(investment);
        investmentCustomer.setCustomer(customer);
        investmentCustomer.setTotalValue(investmentValue);
        investmentCustomer.setQuantity(investmentQuantity);
        investmentCustomer = investmentCustomerRepository.save(investmentCustomer);

        InvestmentTransaction investmentTransaction = new InvestmentTransaction();
        investmentTransaction.setInvestmentCustomer(investmentCustomer);
        investmentTransaction.setType(TransactionInvestmentType.SELL);
        investmentTransaction.setQuantity(quantity);
        investmentTransaction.setValue(transactionValue);
        return transactionRepository.save(investmentTransaction);
    }

    public Page<Transaction> getAllAccountTransactions(Long accountId, Pageable pageable) {
        return transactionRepository.findBankTransactions(accountId, pageable);
    }

}
