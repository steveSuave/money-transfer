package com.agileactors.moneytransfer.service;

import com.agileactors.moneytransfer.model.Account;

import java.math.BigDecimal;

public interface TransferService {

  void depositAmount(Integer accountId, BigDecimal amount, String currency);

  void transferMoney(
      Integer sourceAccountId, Integer targetAccountId, BigDecimal amount, String currency);

  Account getAccount(Integer accountId);

}
