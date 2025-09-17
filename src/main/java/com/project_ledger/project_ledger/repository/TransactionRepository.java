package com.project_ledger.project_ledger.repository;

import com.project_ledger.project_ledger.entity.Transaction;
import com.project_ledger.project_ledger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    List<Transaction> findByUserAndCategoryOrderByTransactionDateDesc(User user, String category);
}