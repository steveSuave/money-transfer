package com.agileactors.moneytransfer.model;

import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("transfer")
public class Transfer {

  @Id private Integer transferId;
  private Integer sourceAccountId;
  private Integer targetAccountId;
  private BigDecimal amount;
  private String currency;

  public Transfer() {}

  public Transfer(
      Integer sourceAccountId, Integer targetAccountId, BigDecimal amount, String currency) {
    this.sourceAccountId = sourceAccountId;
    this.targetAccountId = targetAccountId;
    this.amount = amount;
    this.currency = currency;
  }

  public Integer getTransferId() {
    return transferId;
  }

  public void setTransferId(Integer transferId) {
    this.transferId = transferId;
  }

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
    Transfer transfer = (Transfer) o;
    return Objects.equals(transferId, transfer.transferId)
        && Objects.equals(sourceAccountId, transfer.sourceAccountId)
        && Objects.equals(targetAccountId, transfer.targetAccountId)
        && Objects.equals(amount, transfer.amount)
        && Objects.equals(currency, transfer.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transferId, sourceAccountId, targetAccountId, amount, currency);
  }

  @Override
  public String toString() {
    return "Transfer{"
        + "transferId="
        + transferId
        + ", sourceAccountId="
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
