package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.service.AuthService;
import com.project_ledger.project_ledger.service.PlaidService;
import com.project_ledger.project_ledger.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final AuthService authService;
    private final PlaidService plaidService;
    private final UserService userService;

    public DashboardController(AuthService authService, PlaidService plaidService, UserService userService) {
        this.authService = authService;
        this.plaidService = plaidService;
        this.userService = userService;
    }

    private Long getUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return authService.validateToken(token)
                .map(user -> user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid token"));
    }

    @GetMapping("/accounts")
    public ResponseEntity<Object> getAccounts(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(plaidService.getAccounts(userId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> getTransactions(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(plaidService.getTransactions(userId));
    }

    @GetMapping("/cash-flow")
    public ResponseEntity<Object> getCashFlow(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(plaidService.getCashFlowAnalysis(userId));
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile() {
        // For now, return a default user - in production, extract from JWT token
        return userService.findById(1L)
                .map(user -> ResponseEntity.ok((Object) user))
                .orElse(ResponseEntity.status(404).build());
    }
}