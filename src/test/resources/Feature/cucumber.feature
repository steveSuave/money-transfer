Feature: Testing the money-transfer REST API
  Users should be able to transfer money between accounts

#  Test ac
  Scenario: Happy path for money transfer between two accounts
    Given account with id 1 exists with currency "EUR"
    And account with id 1 has a balance equal to "100" of currency "EUR"
    Then the balance of source account with id 1 should be equal to "100" of currency "EUR"

##  AC 1
#  Scenario: Happy path for money transfer between two accounts
#    Given source account with id 1 exists
#    And target account with id 2 exists
#    And source account has a balance greater or equal to the transaction amount 100
#    When a transaction request is received
#    Then the balance of source account should be debited
#    And the balance of target account should be credited
#
##  AC 2
#  Scenario: Insufficient balance to process money transfer
#    Given source account exists
#    And target account exists
#    And source account has a balance less than the transaction amount
#    When a transaction request is received
#    Then the balance of source account should remain the same
#    And the balance of target account should remain the same
#    And the client of the API should receive an error
#
##  AC 3
#  Scenario: Transfer between same account
#    Given source account exists
#    And both source and target accounts are the same
#    When a transaction request is received
#    Then the balance of source account should remain the same
#    And the client of the API should receive an error
#
##  AC 4
#  Scenario: One or more of the accounts does not exist
#    Given source or target account do not exist
#    When a transaction request is received
#    Then the balance of the existing account should remain the same
#    And the client of the API should receive an error