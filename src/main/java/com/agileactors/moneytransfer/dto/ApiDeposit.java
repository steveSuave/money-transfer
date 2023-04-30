package com.agileactors.moneytransfer.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class ApiDeposit {

  @NotNull private Integer accountId;
  @NotNull private BigDecimal amount;
  @NotNull private String currency;

  public ApiDeposit() {}

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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
    ApiDeposit that = (ApiDeposit) o;
    return Objects.equals(accountId, that.accountId)
        && Objects.equals(amount, that.amount)
        && Objects.equals(currency, that.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, amount, currency);
  }

  @Override
  public String toString() {
    return "ApiTransfer{"
        + "accountId="
        + accountId
        + ", amount="
        + amount
        + ", currency='"
        + currency
        + '\''
        + '}';
  }
}
