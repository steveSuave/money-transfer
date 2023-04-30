package com.agileactors.moneytransfer.model;

import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("account")
public class Account {

  @Id private Integer accountId;
  private BigDecimal balance;
  private String currency;

  public Account() {}

  public Account(Integer accountId, BigDecimal balance, String currency) {
    this.accountId = accountId;
    this.balance = balance;
    this.currency = currency;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(accountId, account.accountId)
        && Objects.equals(balance, account.balance)
        && Objects.equals(currency, account.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, balance, currency);
  }

  @Override
  public String toString() {
    return "Account{"
        + "accountId="
        + accountId
        + ", balance="
        + balance
        + ", currency='"
        + currency
        + '\''
        + '}';
  }
}
