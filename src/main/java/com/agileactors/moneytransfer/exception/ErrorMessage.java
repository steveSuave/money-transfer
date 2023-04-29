package com.agileactors.moneytransfer.exception;

import java.util.Objects;

public class ErrorMessage {

  private String error;

  public ErrorMessage(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorMessage that = (ErrorMessage) o;
    return Objects.equals(error, that.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error);
  }

  @Override
  public String toString() {
    return "ErrorMessage{" + "message='" + error + '\'' + '}';
  }
}
