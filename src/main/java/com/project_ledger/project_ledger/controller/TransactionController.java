package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.entity.Transaction;
import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.repository.TransactionRepository;
import com.project_ledger.project_ledger.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    
    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Map<String, Object> request) {
        User user = userRepository.findById(1L).orElse(null); // Default user for now
        
        Transaction transaction = new Transaction();
        transaction.setDescription((String) request.get("description"));
        transaction.setAmount(new BigDecimal(request.get("amount").toString()));
        transaction.setCategory((String) request.get("category"));
        transaction.setAccount((String) request.get("account"));
        transaction.setUser(user);
        
        if (request.get("date") != null) {
            transaction.setTransactionDate(LocalDate.parse(request.get("date").toString()));
        }
        
        Transaction saved = transactionRepository.save(transaction);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        User user = userRepository.findById(1L).orElse(null);
        List<Transaction> transactions = transactionRepository.findByUserOrderByTransactionDateDesc(user);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(Arrays.asList("Food & Dining", "Shopping", "Transportation", "Entertainment", "Bills", "Income", "Healthcare", "Education", "Travel", "Other"));
    }
}