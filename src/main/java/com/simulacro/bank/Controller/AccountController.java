package com.simulacro.bank.Controller;

import com.simulacro.bank.DTO.AccountDTO;
import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.CurrentAccount;
import com.simulacro.bank.model.SavingsAccount;
import com.simulacro.bank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Accounts", description = "Database operations for accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/pageable")
    @Operation(summary = "Get accounts", description = "Gets all accounts registered in the bank")
    public ResponseEntity<Page<AccountDTO>> getAllAccounts(Pageable pageable) {
        return new ResponseEntity<>(accountService.getAllAccounts(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by Id", description = "Gets the account with the ID on the path")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get account by Id", description = "Gets all accounts of the customer with the Id in the path")
    public ResponseEntity<List<AccountDTO>> getAccountFromCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccountFromCustomer(id), HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Create a current account", description = "Create a Current Account passing its accountType(CURRENT/SAVINGS),number account, agency, credit Limit, and his ID")
    public ResponseEntity<AccountDTO> createCurrentAccount(@RequestBody AccountDTO account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an account", description = "Update the account with the id of the path with new information")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO account) {
        return new ResponseEntity<>(accountService.updateAccount(account, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account", description = "Delete the account with the id in the path")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok("Account deleted successfully!");
    }

}
