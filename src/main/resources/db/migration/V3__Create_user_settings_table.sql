-- Add new columns to users table
ALTER TABLE users 
ADD COLUMN full_name VARCHAR(255),
ADD COLUMN phone_number VARCHAR(20),
ADD COLUMN date_of_birth DATE;

-- Create user_settings table
CREATE TABLE user_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    currency VARCHAR(10) DEFAULT 'USD',
    country VARCHAR(100),
    timezone VARCHAR(100),
    language VARCHAR(50) DEFAULT 'English',
    monthly_budget DECIMAL(10,2),
    daily_limit DECIMAL(10,2),
    food_budget DECIMAL(10,2),
    transport_budget DECIMAL(10,2),
    entertainment_budget DECIMAL(10,2),
    shopping_budget DECIMAL(10,2),
    utilities_budget DECIMAL(10,2),
    transaction_alerts BOOLEAN DEFAULT TRUE,
    budget_reports BOOLEAN DEFAULT TRUE,
    security_alerts BOOLEAN DEFAULT TRUE,
    bill_reminders BOOLEAN DEFAULT TRUE,
    budget_alerts BOOLEAN DEFAULT TRUE,
    promotional_offers BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);