package com.agileactors.moneytransfer.service;

import java.math.BigDecimal;

public interface TransferService {

  void transferMoney(
      Integer sourceAccountId, Integer targetAccountId, BigDecimal amount, String currency);
}
