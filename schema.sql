CREATE TABLE IF NOT EXISTS currencies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(3) NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    sign VARCHAR(3) NOT NULL
);
CREATE TABLE IF NOT EXISTS exchange_rates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    base_currency_id INT NOT NULL,
    target_currency_id INT NOT NULL,
    rate DECIMAL(6) NOT NULL
)
