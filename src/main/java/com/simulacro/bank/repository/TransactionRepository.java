package com.simulacro.bank.model.repository;

import com.simulacro.bank.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE TYPE(t) = BankTransaction AND t.originAccount.id = :accountId")
    Page<Transaction> findBankTransactions(@Param("accountId") Long accountId, Pageable pageable);

}
