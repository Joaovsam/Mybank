package com.simulacro.bank.Controller;

import com.simulacro.bank.DTO.TransactionDTO;
import com.simulacro.bank.model.Transaction;
import com.simulacro.bank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.deposit(transactionDTO.getDestinationAccount(), transactionDTO.getValue()), HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.withdraw(transactionDTO.getOriginAccount(), transactionDTO.getValue()), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.transfer(transactionDTO.getOriginAccount(),
                transactionDTO.getDestinationAccount(), transactionDTO.getValue()), HttpStatus.OK);
    }

    @GetMapping("/{id}/pageable")
    public ResponseEntity<Page<Transaction>> getAllAccountTransaction(@PathVariable Long id, Pageable pageable) {
        return new ResponseEntity<>(transactionService.getAllAccountTransactions(id, pageable), HttpStatus.OK);
    }

}
