package com.project_ledger.project_ledger.service;

import com.project_ledger.project_ledger.dto.AuthResponse;
import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, Long> tokenUserIdMap = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponse register(String email, String password, String name, String companyName) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User already exists with this email");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setCompanyName(companyName);

        User savedUser = userRepository.save(user);
        String token = generateToken(savedUser.getId());

        return new AuthResponse(token, savedUser);
    }

    public Optional<AuthResponse> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    String token = generateToken(user.getId());
                    return new AuthResponse(token, user);
                });
    }

    public Optional<User> validateToken(String token) {
        Long userId = tokenUserIdMap.get(token);
        if (userId != null) {
            return userRepository.findById(userId);
        }
        return Optional.empty();
    }

    public void logout(String token) {
        tokenUserIdMap.remove(token);
    }

    private String generateToken(Long userId) {
        String token = UUID.randomUUID().toString();
        tokenUserIdMap.put(token, userId);
        return token;
    }

    // Legacy methods for backward compatibility
    public User signUp(String email, String plainPassword) {
        return register(email, plainPassword, null, null).getUser();
    }
}