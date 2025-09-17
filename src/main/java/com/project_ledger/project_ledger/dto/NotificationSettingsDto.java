package com.project_ledger.project_ledger.dto;

public class NotificationSettingsDto {
    private boolean transactionAlerts;
    private boolean budgetReports;
    private boolean securityAlerts;
    private boolean billReminders;
    private boolean budgetAlerts;
    private boolean promotionalOffers;

    public NotificationSettingsDto() {}

    public NotificationSettingsDto(boolean transactionAlerts, boolean budgetReports, boolean securityAlerts,
                                  boolean billReminders, boolean budgetAlerts, boolean promotionalOffers) {
        this.transactionAlerts = transactionAlerts;
        this.budgetReports = budgetReports;
        this.securityAlerts = securityAlerts;
        this.billReminders = billReminders;
        this.budgetAlerts = budgetAlerts;
        this.promotionalOffers = promotionalOffers;
    }

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