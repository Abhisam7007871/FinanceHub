package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.entity.Account;
import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.repository.AccountRepository;
import com.project_ledger.project_ledger.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    
    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Map<String, Object> request) {
        User user = userRepository.findById(1L).orElse(null);
        
        Account account = new Account();
        account.setName((String) request.get("name"));
        account.setType((String) request.get("type"));
        account.setBalance(new BigDecimal(request.get("balance").toString()));
        account.setCurrency((String) request.getOrDefault("currency", "USD"));
        account.setUser(user);
        
        Account saved = accountRepository.save(account);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        User user = userRepository.findById(1L).orElse(null);
        List<Account> accounts = accountRepository.findByUser(user);
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sync")
    public ResponseEntity<Map<String, String>> syncAccount(@PathVariable Long id) {
        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "Account synced successfully");
        return ResponseEntity.ok(result);
    }
}