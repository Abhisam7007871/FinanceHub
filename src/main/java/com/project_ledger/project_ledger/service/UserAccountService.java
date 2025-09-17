package com.project_ledger.project_ledger.service;

import com.project_ledger.project_ledger.dto.BudgetSettingsDto;
import com.project_ledger.project_ledger.dto.NotificationSettingsDto;
import com.project_ledger.project_ledger.dto.UserProfileDto;
import com.project_ledger.project_ledger.entity.User;
import com.project_ledger.project_ledger.entity.UserSettings;
import com.project_ledger.project_ledger.repository.UserRepository;
import com.project_ledger.project_ledger.repository.UserSettingsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAccountService {
    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(UserRepository userRepository, UserSettingsRepository userSettingsRepository, 
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings());

        return new UserProfileDto(
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                settings.getCurrency(),
                settings.getCountry(),
                settings.getTimezone(),
                settings.getLanguage()
        );
    }

    public UserProfileDto updateUserProfile(Long userId, UserProfileDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(profileDto.getFullName());
        user.setEmail(profileDto.getEmail());
        user.setPhoneNumber(profileDto.getPhoneNumber());
        user.setDateOfBirth(profileDto.getDateOfBirth());
        userRepository.save(user);

        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings(userId));
        
        settings.setCurrency(profileDto.getCurrency());
        settings.setCountry(profileDto.getCountry());
        settings.setTimezone(profileDto.getTimezone());
        settings.setLanguage(profileDto.getLanguage());
        userSettingsRepository.save(settings);

        return profileDto;
    }

    public BudgetSettingsDto getBudgetSettings(Long userId) {
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings());

        Map<String, BigDecimal> categoryBudgets = new HashMap<>();
        categoryBudgets.put("food", settings.getFoodBudget());
        categoryBudgets.put("transport", settings.getTransportBudget());
        categoryBudgets.put("entertainment", settings.getEntertainmentBudget());
        categoryBudgets.put("shopping", settings.getShoppingBudget());
        categoryBudgets.put("utilities", settings.getUtilitiesBudget());

        return new BudgetSettingsDto(
                settings.getMonthlyBudget(),
                settings.getDailyLimit(),
                categoryBudgets
        );
    }

    public BudgetSettingsDto updateBudgetSettings(Long userId, BudgetSettingsDto budgetDto) {
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings(userId));

        settings.setMonthlyBudget(budgetDto.getMonthlyBudget());
        settings.setDailyLimit(budgetDto.getDailyLimit());
        
        Map<String, BigDecimal> categoryBudgets = budgetDto.getCategoryBudgets();
        if (categoryBudgets != null) {
            settings.setFoodBudget(categoryBudgets.get("food"));
            settings.setTransportBudget(categoryBudgets.get("transport"));
            settings.setEntertainmentBudget(categoryBudgets.get("entertainment"));
            settings.setShoppingBudget(categoryBudgets.get("shopping"));
            settings.setUtilitiesBudget(categoryBudgets.get("utilities"));
        }

        userSettingsRepository.save(settings);
        return budgetDto;
    }

    public NotificationSettingsDto getNotificationSettings(Long userId) {
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings());

        return new NotificationSettingsDto(
                settings.isTransactionAlerts(),
                settings.isBudgetReports(),
                settings.isSecurityAlerts(),
                settings.isBillReminders(),
                settings.isBudgetAlerts(),
                settings.isPromotionalOffers()
        );
    }

    public NotificationSettingsDto updateNotificationSettings(Long userId, NotificationSettingsDto notificationDto) {
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings(userId));

        settings.setTransactionAlerts(notificationDto.isTransactionAlerts());
        settings.setBudgetReports(notificationDto.isBudgetReports());
        settings.setSecurityAlerts(notificationDto.isSecurityAlerts());
        settings.setBillReminders(notificationDto.isBillReminders());
        settings.setBudgetAlerts(notificationDto.isBudgetAlerts());
        settings.setPromotionalOffers(notificationDto.isPromotionalOffers());

        userSettingsRepository.save(settings);
        return notificationDto;
    }

    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}