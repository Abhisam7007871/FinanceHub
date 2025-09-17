package com.project_ledger.project_ledger.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "currency")
    private String currency = "USD";

    @Column(name = "country")
    private String country;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "language")
    private String language = "English";

    @Column(name = "monthly_budget", precision = 10, scale = 2)
    private BigDecimal monthlyBudget;

    @Column(name = "daily_limit", precision = 10, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "food_budget", precision = 10, scale = 2)
    private BigDecimal foodBudget;

    @Column(name = "transport_budget", precision = 10, scale = 2)
    private BigDecimal transportBudget;

    @Column(name = "entertainment_budget", precision = 10, scale = 2)
    private BigDecimal entertainmentBudget;

    @Column(name = "shopping_budget", precision = 10, scale = 2)
    private BigDecimal shoppingBudget;

    @Column(name = "utilities_budget", precision = 10, scale = 2)
    private BigDecimal utilitiesBudget;

    @Column(name = "transaction_alerts")
    private boolean transactionAlerts = true;

    @Column(name = "budget_reports")
    private boolean budgetReports = true;

    @Column(name = "security_alerts")
    private boolean securityAlerts = true;

    @Column(name = "bill_reminders")
    private boolean billReminders = true;

    @Column(name = "budget_alerts")
    private boolean budgetAlerts = true;

    @Column(name = "promotional_offers")
    private boolean promotionalOffers = false;

    public UserSettings() {}

    public UserSettings(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public BigDecimal getMonthlyBudget() { return monthlyBudget; }
    public void setMonthlyBudget(BigDecimal monthlyBudget) { this.monthlyBudget = monthlyBudget; }

    public BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }

    public BigDecimal getFoodBudget() { return foodBudget; }
    public void setFoodBudget(BigDecimal foodBudget) { this.foodBudget = foodBudget; }

    public BigDecimal getTransportBudget() { return transportBudget; }
    public void setTransportBudget(BigDecimal transportBudget) { this.transportBudget = transportBudget; }

    public BigDecimal getEntertainmentBudget() { return entertainmentBudget; }
    public void setEntertainmentBudget(BigDecimal entertainmentBudget) { this.entertainmentBudget = entertainmentBudget; }

    public BigDecimal getShoppingBudget() { return shoppingBudget; }
    public void setShoppingBudget(BigDecimal shoppingBudget) { this.shoppingBudget = shoppingBudget; }

    public BigDecimal getUtilitiesBudget() { return utilitiesBudget; }
    public void setUtilitiesBudget(BigDecimal utilitiesBudget) { this.utilitiesBudget = utilitiesBudget; }

    public boolean isTransactionAlerts() { return transactionAlerts; }
    public void setTransactionAlerts(boolean transactionAlerts) { this.transactionAlerts = transactionAlerts; }

    public boolean isBudgetReports() { return budgetReports; }
    public void setBudgetReports(boolean budgetReports) { this.budgetReports = budgetReports; }

    public boolean isSecurityAlerts() { return securityAlerts; }
    public void setSecurityAlerts(boolean securityAlerts) { this.securityAlerts = securityAlerts; }

    public boolean isBillReminders() { return billReminders; }
    public void setBillReminders(boolean billReminders) { this.billReminders = billReminders; }

    public boolean isBudgetAlerts() { return budgetAlerts; }
    public void setBudgetAlerts(boolean budgetAlerts) { this.budgetAlerts = budgetAlerts; }

    public boolean isPromotionalOffers() { return promotionalOffers; }
    public void setPromotionalOffers(boolean promotionalOffers) { this.promotionalOffers = promotionalOffers; }
}