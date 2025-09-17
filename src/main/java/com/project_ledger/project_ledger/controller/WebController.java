package com.project_ledger.project_ledger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String home() {
        return "forward:/html/index.html";
    }
    
    @GetMapping("/login")
    public String login() {
        return "forward:/html/login.html";
    }
    
    @GetMapping("/register")
    public String register() {
        return "forward:/html/register.html";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/html/dashboard.html";
    }
    
    @GetMapping("/account-management")
    public String accountManagement() {
        return "forward:/html/account-management.html";
    }
    
    @GetMapping("/settings")
    public String settings() {
        return "forward:/html/settings.html";
    }
    
    @GetMapping("/data")
    public String data() {
        return "forward:/html/data.html";
    }
    
    @GetMapping("/accounts")
    public String accounts() {
        return "forward:/html/accounts.html";
    }
    
    @GetMapping("/transactions")
    public String transactions() {
        return "forward:/html/transactions.html";
    }
    
    @GetMapping("/analytics")
    public String analytics() {
        return "forward:/html/analytics.html";
    }
    
    @GetMapping("/link-accounts")
    public String linkAccounts() {
        return "forward:/html/link-accounts.html";
    }
    
    @GetMapping("/reports")
    public String reports() {
        return "forward:/html/reports.html";
    }
}