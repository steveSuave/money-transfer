package com.agileactors.moneytransfer.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class ApiTransfer {

  @NotNull private Integer sourceAccountId;
  @NotNull private Integer targetAccountId;
  @NotNull private BigDecimal amount;
  @NotNull private String currency;

  public ApiTransfer() {}

  public Integer getSourceAccountId() {
    return sourceAccountId;
  }

  public void setSourceAccountId(Integer sourceAccountId) {
    this.sourceAccountId = sourceAccountId;
  }

  public Integer getTargetAccountId() {
    return targetAccountId;
  }

  public void setTargetAccountId(Integer targetAccountId) {
    this.targetAccountId = targetAccountId;
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
    ApiTransfer that = (ApiTransfer) o;
    return Objects.equals(sourceAccountId, that.sourceAccountId)
        && Objects.equals(targetAccountId, that.targetAccountId)
        && Objects.equals(amount, that.amount)
        && Objects.equals(currency, that.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceAccountId, targetAccountId, amount, currency);
  }

  @Override
  public String toString() {
    return "ApiTransfer{"
        + "sourceAccountId="
        + sourceAccountId
        + ", targetAccountId="
        + targetAccountId
        + ", amount="
        + amount
        + ", currency='"
        + currency
        + '\''
        + '}';
  }
}
