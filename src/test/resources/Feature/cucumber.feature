Feature: Testing the money-transfer REST API
  Users should be able to transfer money between accounts
  The test source and target accounts both have a currency of EUR in their test accounts

#  AC 1
  Scenario: Happy path for money transfer between two accounts
    Given source account has a balance equal to "100.00"
    And the target account has a balance equal to "0.00"
    When a transaction request for "50.00" "EUR" is received
    Then the balance of the source account should be equal to "50.00"
    And the balance of the target account should be equal to "50.00"

#  AC 2
  Scenario: Insufficient balance to process money transfer
    Given source account has a balance equal to "10.00"
    And the target account has a balance equal to "0.00"
    When a transaction request for "20.00" "EUR" is received
    Then the balance of the source account should be equal to "10.00"
    And the balance of the target account should be equal to "0.00"
    And the client of the API should receive an error with http status of 409

#  AC 3
  Scenario: Transfer between same account
    Given source account has a balance equal to "100.00"
    When a transaction request is received from the source account to itself
    Then the balance of the source account should be equal to "100.00"
    And the client of the API should receive an error with http status of 409

#  AC 4
  Scenario: One or more of the accounts does not exist
    Given source account has a balance equal to "100.00"
    When a transaction request is received with a nonexistent target account
    Then the balance of the source account should be equal to "100.00"
    And the client of the API should receive an error with http status of 409
