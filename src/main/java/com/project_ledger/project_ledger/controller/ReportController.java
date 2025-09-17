package com.project_ledger.project_ledger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping("/income-statement")
    public ResponseEntity<Map<String, Object>> getIncomeStatement(@RequestParam(defaultValue = "30") int days) {
        Map<String, Object> report = Map.of(
            "totalIncome", 8350.00,
            "totalExpenses", 3240.50,
            "netIncome", 5109.50,
            "period", days + " days"
        );
        return ResponseEntity.ok(report);
    }

    @GetMapping("/net-worth")
    public ResponseEntity<Map<String, Object>> getNetWorth() {
        Map<String, Object> report = Map.of(
            "assets", 24582.75,
            "liabilities", 2450.30,
            "netWorth", 22132.45
        );
        return ResponseEntity.ok(report);
    }

    @GetMapping("/spending-by-category")
    public ResponseEntity<List<Map<String, Object>>> getSpendingByCategory(@RequestParam(defaultValue = "30") int days) {
        List<Map<String, Object>> spending = Arrays.asList(
            Map.of("category", "Food & Dining", "amount", 320.50, "percentage", 35),
            Map.of("category", "Shopping", "amount", 140.25, "percentage", 25),
            Map.of("category", "Transportation", "amount", 90.75, "percentage", 15)
        );
        return ResponseEntity.ok(spending);
    }

    @PostMapping("/export")
    public ResponseEntity<Map<String, String>> exportReport(@RequestBody Map<String, String> request) {
        Map<String, String> result = Map.of(
            "downloadUrl", "/api/reports/download/" + UUID.randomUUID(),
            "format", request.getOrDefault("format", "PDF"),
            "status", "ready"
        );
        return ResponseEntity.ok(result);
    }
}