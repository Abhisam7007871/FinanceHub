package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        User user = userRepository.findById(1L).orElse(null);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody Map<String, Object> request) {
        User user = userRepository.findById(1L).orElse(null);
        if (user != null) {
            if (request.get("name") != null) user.setName((String) request.get("name"));
            if (request.get("email") != null) user.setEmail((String) request.get("email"));
            if (request.get("companyName") != null) user.setCompanyName((String) request.get("companyName"));
            userRepository.save(user);
        }
        return ResponseEntity.ok(user);
    }
}