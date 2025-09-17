package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.entity.AppUser;
import com.project_ledger.project_ledger.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestOAuthController {

    @GetMapping("/oauth-integration")
    public Object testOAuthIntegration(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return Map.of(
                "status", "not_authenticated",
                "message", "No authentication found"
            );
        }

        Object principal = authentication.getPrincipal();
        
        if (principal instanceof AppUser) {
            AppUser appUser = (AppUser) principal;
            User linkedUser = appUser.getUser();
            
            return Map.of(
                "status", "oauth_authenticated",
                "authType", "OAuth2",
                "provider", appUser.getProvider(),
                "oauthUser", Map.of(
                    "id", appUser.getId(),
                    "email", appUser.getEmail(),
                    "name", appUser.getName(),
                    "provider", appUser.getProvider()
                ),
                "linkedUser", linkedUser != null ? Map.of(
                    "id", linkedUser.getId(),
                    "email", linkedUser.getEmail(),
                    "name", linkedUser.getName()
                ) : null,
                "message", "OAuth2 user authenticated successfully"
            );
        }
        
        return Map.of(
            "status", "other_auth",
            "authType", "Traditional",
            "principal", principal.toString(),
            "message", "Non-OAuth2 authentication"
        );
    }

    @GetMapping("/protected")
    public Object testProtectedEndpoint(Authentication authentication) {
        return Map.of(
            "message", "This is a protected endpoint",
            "authenticated", authentication != null,
            "timestamp", System.currentTimeMillis()
        );
    }
}