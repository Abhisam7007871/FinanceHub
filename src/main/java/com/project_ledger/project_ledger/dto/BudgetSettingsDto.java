package com.project_ledger.project_ledger.dto;

import java.math.BigDecimal;
import java.util.Map;

public class BudgetSettingsDto {
    private BigDecimal monthlyBudget;
    private BigDecimal dailyLimit;
    private Map<String, BigDecimal> categoryBudgets;

    public BudgetSettingsDto() {}

    public BudgetSettingsDto(BigDecimal monthlyBudget, BigDecimal dailyLimit, Map<String, BigDecimal> categoryBudgets) {
        this.monthlyBudget = monthlyBudget;
        this.dailyLimit = dailyLimit;
        this.categoryBudgets = categoryBudgets;
    }

    public BigDecimal getMonthlyBudget() { return monthlyBudget; }
    public void setMonthlyBudget(BigDecimal monthlyBudget) { this.monthlyBudget = monthlyBudget; }

    public BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }

    public Map<String, BigDecimal> getCategoryBudgets() { return categoryBudgets; }
    public void setCategoryBudgets(Map<String, BigDecimal> categoryBudgets) { this.categoryBudgets = categoryBudgets; }
}