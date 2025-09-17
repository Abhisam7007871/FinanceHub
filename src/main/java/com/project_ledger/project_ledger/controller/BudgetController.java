package com.project_ledger.project_ledger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getBudgets() {
        List<Map<String, Object>> budgets = Arrays.asList(
            Map.of("category", "Food & Dining", "budgeted", 500, "spent", 320, "remaining", 180),
            Map.of("category", "Shopping", "budgeted", 300, "spent", 140, "remaining", 160),
            Map.of("category", "Transportation", "budgeted", 200, "spent", 90, "remaining", 110)
        );
        return ResponseEntity.ok(budgets);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBudget(@RequestBody Map<String, Object> budget) {
        budget.put("id", UUID.randomUUID().toString());
        budget.put("createdAt", new Date());
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBudget(@PathVariable String id, @RequestBody Map<String, Object> budget) {
        budget.put("id", id);
        budget.put("updatedAt", new Date());
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
}