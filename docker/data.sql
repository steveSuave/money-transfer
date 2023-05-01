CREATE DATABASE IF NOT EXISTS small_bank;

USE small_bank;

CREATE TABLE account (
  account_id INT NOT NULL AUTO_INCREMENT,
  balance DECIMAL(10, 2) NOT NULL,
  currency CHAR(45) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (account_id)
);

CREATE TABLE transfer (
  transfer_id INT NOT NULL AUTO_INCREMENT,
  source_account_id INT NOT NULL,
  target_account_id INT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  currency CHAR(45) NOT NULL,
  PRIMARY KEY (transfer_id),
  FOREIGN KEY (source_account_id) REFERENCES account(account_id),
  FOREIGN KEY (target_account_id) REFERENCES account(account_id),
  INDEX source_target_idx (source_account_id, target_account_id)
);

-- Test data for cucumber scenarios
INSERT INTO account (balance, currency) VALUES (0, 'EUR');
INSERT INTO account (balance, currency) VALUES (0, 'EUR');
