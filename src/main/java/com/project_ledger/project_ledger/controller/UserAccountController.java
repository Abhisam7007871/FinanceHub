package com.project_ledger.project_ledger.controller;

import com.project_ledger.project_ledger.dto.UserProfileDto;
import com.project_ledger.project_ledger.dto.BudgetSettingsDto;
import com.project_ledger.project_ledger.dto.NotificationSettingsDto;
import com.project_ledger.project_ledger.service.AuthService;
import com.project_ledger.project_ledger.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class UserAccountController {
    private final AuthService authService;
    private final UserAccountService userAccountService;

    public UserAccountController(AuthService authService, UserAccountService userAccountService) {
        this.authService = authService;
        this.userAccountService = userAccountService;
    }

    private Long getUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return authService.validateToken(token)
                .map(user -> user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid token"));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(userAccountService.getUserProfile(userId));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(@RequestHeader("Authorization") String authHeader, 
                                                        @RequestBody UserProfileDto profileDto) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(userAccountService.updateUserProfile(userId, profileDto));
    }

    @GetMapping("/budget")
    public ResponseEntity<BudgetSettingsDto> getBudgetSettings(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(userAccountService.getBudgetSettings(userId));
    }

    @PutMapping("/budget")
    public ResponseEntity<BudgetSettingsDto> updateBudgetSettings(@RequestHeader("Authorization") String authHeader,
                                                                 @RequestBody BudgetSettingsDto budgetDto) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(userAccountService.updateBudgetSettings(userId, budgetDto));
    }

    @GetMapping("/notifications")
    public ResponseEntity<NotificationSettingsDto> getNotificationSettings(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(userAccountService.getNotificationSettings(userId));
    }

    @PutMapping("/notifications")
    public ResponseEntity<NotificationSettingsDto> updateNotificationSettings(@RequestHeader("Authorization") String authHeader,
                                                                             @RequestBody NotificationSettingsDto notificationDto) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(userAccountService.updateNotificationSettings(userId, notificationDto));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody ChangePasswordRequest request) {
        Long userId = getUserIdFromToken(authHeader);
        userAccountService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }

    public static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;

        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}