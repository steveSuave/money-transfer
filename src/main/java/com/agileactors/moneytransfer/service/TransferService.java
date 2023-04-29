package com.agileactors.moneytransfer.service;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dao.TransferRepository;
import com.agileactors.moneytransfer.model.Account;
import com.agileactors.moneytransfer.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class TransferService {

  private final TransferRepository transferRepository;
  private final AccountRepository accountRepository;

  @Autowired
  public TransferService(
      TransferRepository transferRepository, AccountRepository accountRepository) {
    this.transferRepository = transferRepository;
    this.accountRepository = accountRepository;
  }

  @Transactional
  public void transferMoney(
      Integer sourceAccountId, Integer targetAccountId, BigDecimal amount, String currency) {

    List<Account> accounts =
        accountRepository.findByAccountIds(Arrays.asList(sourceAccountId, targetAccountId));

    validateTransfer(accounts, amount, currency);

    Account sourceAccount =
        accounts.stream()
            .filter(account -> account.getAccountId().equals(sourceAccountId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Nonexistent source account"));

    Account targetAccount =
        accounts.stream()
            .filter(account -> account.getAccountId().equals(targetAccountId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Nonexistent target account"));

    Transfer transfer = new Transfer();
    transfer.setSourceAccountId(sourceAccountId);
    transfer.setTargetAccountId(targetAccountId);
    transfer.setAmount(amount);
    transfer.setCurrency(currency);

    sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
    targetAccount.setBalance(targetAccount.getBalance().add(amount));

    transferRepository.save(transfer);
    accountRepository.save(sourceAccount);
    accountRepository.save(targetAccount);
  }

  private void validateTransfer(List<Account> accounts, BigDecimal amount, String currency) {

    Assert.isTrue(
        accounts.stream().map(Account::getAccountId).distinct().count() == 2,
        "Source and target account ids must be different");

    // convert to common currency and compare amount with balance

  }

  private void convertToCommonCurrency(List<Account> accounts, String currency) {
    // convert to common currency

  }

}
