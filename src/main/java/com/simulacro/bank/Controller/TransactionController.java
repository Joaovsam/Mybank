package com.simulacro.bank.Controller;

import com.simulacro.bank.DTO.InvestmentTransactionDTO;
import com.simulacro.bank.DTO.TransactionDTO;
import com.simulacro.bank.model.Transaction;
import com.simulacro.bank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
@Tag(name = "Transactions", description = "Database operations for bank and investment transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit", description = "Deposit money with a DTO with the destination account infomation and how much money you want to deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.deposit(transactionDTO.getDestinationAccount(), transactionDTO.getValue()));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "withdraw", description = "Withdraw money with a DTO with the origin account information and her password")
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.withdraw(transactionDTO.getOriginAccount(), transactionDTO.getValue()));
    }

    @PostMapping("/transfer")
    @Operation(summary = "tansfer", description = "transfer money frorm a origin account to destination account")
    public ResponseEntity<Transaction> transfer(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.transfer(transactionDTO.getOriginAccount(),
                transactionDTO.getDestinationAccount(), transactionDTO.getValue()));
    }

    @PostMapping("/buyInvestment")
    @Operation(summary = "buy investment", description = "Buy investments passing investment id, the "
            + "account you are buying id, the aplication price, and the tax for the transaction")
    public ResponseEntity<Transaction> buyInvestment(@RequestBody InvestmentTransactionDTO investmentTransactionDTO) {
        return ResponseEntity.ok(transactionService.buyInvestment(investmentTransactionDTO.getInvestmentId(), investmentTransactionDTO.getAccountId(),
                investmentTransactionDTO.getAplicationPrice(), investmentTransactionDTO.getTransactionTax()));
    }

    @PostMapping("/sellInvestment")
    @Operation(summary = "sell investment", description = "Sell investments passing Custommer Investment id, the "
            + "account you are transaction for id, the quantity of investment you wanna sell, and the tax for the transaction")
    public ResponseEntity<Transaction> sellInvestment(@RequestBody InvestmentTransactionDTO investmentTransactionDTO) {
        return ResponseEntity.ok(transactionService.sellInvestment(investmentTransactionDTO.getInvestmentCustomerId(), investmentTransactionDTO.getAccountId(),
                investmentTransactionDTO.getQuantity(), investmentTransactionDTO.getTransactionTax()));
    }

    @GetMapping("/{id}/pageable")
    @Operation(summary = "Account transaction", description = "get all account transactions from a customer")
    public ResponseEntity<Page<Transaction>> getAllAccountTransaction(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllAccountTransactions(id, pageable));
    }

}
