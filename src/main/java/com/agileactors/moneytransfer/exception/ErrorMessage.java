package com.agileactors.moneytransfer.exception;

import java.util.Objects;

public class ErrorMessage {

  private String error;
  private String traceId;

  public ErrorMessage(String error, String traceId) {
    this.error = error;
    this.traceId = traceId;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorMessage that = (ErrorMessage) o;
    return Objects.equals(error, that.error) && Objects.equals(traceId, that.traceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, traceId);
  }

  @Override
  public String toString() {
    return "ErrorMessage{" + "error='" + error + '\'' + ", traceId='" + traceId + '\'' + '}';
  }
}
