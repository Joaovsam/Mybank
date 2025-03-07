package com.simulacro.bank.Controller;

import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.CurrentAccount;
import com.simulacro.bank.model.SavingsAccount;
import com.simulacro.bank.service.AccountService;
import java.util.List;
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
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/pageable")
    public ResponseEntity<Page<Account>> getAllAccounts(Pageable pageable) {
        return new ResponseEntity<>(accountService.getAllAccounts(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Account>> getAccountFromCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccountFromCustomer(id), HttpStatus.OK);
    }

    @PostMapping("/current")
    public ResponseEntity<Account> createCurrentAccount(@RequestBody CurrentAccount account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.OK);
    }

    @PostMapping("/savings")
    public ResponseEntity<Account> createSavingAccount(@RequestBody SavingsAccount account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return new ResponseEntity<>(accountService.updateAccount(account, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok("Account deleted successfully!");
    }

}
