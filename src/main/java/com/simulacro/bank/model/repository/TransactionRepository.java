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

    @Query("SELECT t FROM Transacao t WHERE t.originAccount.id = :accountId OR t.destinationAccount.id = :accountId")
    Page<Transaction> findByContaId(@Param("accountId") Long contaId, Pageable pageable);

}
