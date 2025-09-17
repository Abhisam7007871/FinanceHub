package com.project_ledger.project_ledger.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PlaidService {

    public Map<String, String> createLinkToken(Long userId) {
        // Simulate Plaid API response
        String linkToken = "link-sandbox-" + UUID.randomUUID();
        String expiration = "2024-12-31T23:59:59Z";
        
        return Map.of(
            "linkToken", linkToken,
            "expiration", expiration
        );
    }

    public void exchangePublicToken(String publicToken, Long userId) {
        // Simulate exchanging public token for access token
        String accessToken = "access-sandbox-" + UUID.randomUUID();
        String institutionId = "ins_123";
        
        // In real implementation, store this with UserService
        System.out.println("Stored access token for user: " + userId);
    }

    public Map<String, Object> getAccounts(Long userId) {
        return Map.of(
            "accounts", List.of(
                Map.of("name", "Checking Account", "balance", 12500.50, "type", "depository"),
                Map.of("name", "Savings Account", "balance", 27500.75, "type", "depository"),
                Map.of("name", "Credit Card", "balance", -2450.00, "type", "credit")
            )
        );
    }

    public Map<String, Object> getTransactions(Long userId) {
        return Map.of(
            "transactions", List.of(
                Map.of("date", "2024-01-15", "amount", -45.67, "name", "Coffee Shop", "category", "Food and Drink"),
                Map.of("date", "2024-01-14", "amount", -125.00, "name", "Grocery Store", "category", "Shops"),
                Map.of("date", "2024-01-13", "amount", 2500.00, "name", "Client Payment", "category", "Deposit"),
                Map.of("date", "2024-01-12", "amount", -89.99, "name", "Software Subscription", "category", "Service")
            )
        );
    }

    public Map<String, Object> getCashFlowAnalysis(Long userId) {
        return Map.of(
            "income", 12500.00,
            "expenses", 8450.00,
            "netCashFlow", 4050.00,
            "projectedBalance", 28750.00,
            "monthlyTrend", List.of(
                Map.of("month", "Jan", "income", 12500, "expenses", 8450),
                Map.of("month", "Feb", "income", 13200, "expenses", 8900),
                Map.of("month", "Mar", "income", 11800, "expenses", 8200)
            )
        );
    }
}