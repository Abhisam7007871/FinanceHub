package com.project_ledger.project_ledger.service;

import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }
            if (updatedUser.getCompanyName() != null) {
                existingUser.setCompanyName(updatedUser.getCompanyName());
            }
            return userRepository.save(existingUser);
        });
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void addPlaidAccessToken(Long userId, String institutionId, String accessToken) {
        userRepository.findById(userId).ifPresent(user -> {
            user.getPlaidAccessTokens().put(institutionId, accessToken);
            userRepository.save(user);
        });
    }
}