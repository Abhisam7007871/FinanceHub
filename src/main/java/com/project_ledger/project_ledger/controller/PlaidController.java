package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.service.AuthService;
import com.project_ledger.project_ledger.service.PlaidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/plaid")
public class PlaidController {
    private final AuthService authService;
    private final PlaidService plaidService;

    public PlaidController(AuthService authService, PlaidService plaidService) {
        this.authService = authService;
        this.plaidService = plaidService;
    }

    @PostMapping("/link-token")
    public ResponseEntity<Map<String, String>> createLinkToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = authService.validateToken(token)
                .map(user -> user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        
        Map<String, String> response = plaidService.createLinkToken(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exchange-token")
    public ResponseEntity<Void> exchangePublicToken(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String publicToken) {
        
        String token = authHeader.replace("Bearer ", "");
        Long userId = authService.validateToken(token)
                .map(user -> user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        
        plaidService.exchangePublicToken(publicToken, userId);
        return ResponseEntity.ok().build();
    }
}