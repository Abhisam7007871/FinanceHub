package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.dto.AuthResponse;
import com.project_ledger.project_ledger.dto.LoginRequest;
import com.project_ledger.project_ledger.dto.SignUpRequest;
import com.project_ledger.project_ledger.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest request) {
        AuthResponse response = authService.register(request.getEmail(), request.getPassword(), null, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Optional<AuthResponse> response = authService.login(request.getEmail(), request.getPassword());
        return response.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.badRequest().build());
    }
}