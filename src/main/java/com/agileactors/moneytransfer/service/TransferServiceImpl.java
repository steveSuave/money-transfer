package com.agileactors.moneytransfer.service;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dao.TransferRepository;
import com.agileactors.moneytransfer.model.Account;
import com.agileactors.moneytransfer.model.Transfer;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.agileactors.moneytransfer.monetaryhelper.CurrencyConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TransferServiceImpl implements TransferService {

  private final TransferRepository transferRepository;
  private final AccountRepository accountRepository;

  @Autowired
  public TransferServiceImpl(
      TransferRepository transferRepository, AccountRepository accountRepository) {
    this.transferRepository = transferRepository;
    this.accountRepository = accountRepository;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  public synchronized void transferMoney(
      Integer sourceAccountId, Integer targetAccountId, BigDecimal amount, String currency) {

    List<Account> accounts =
        accountRepository.findByAccountIds(Arrays.asList(sourceAccountId, targetAccountId));

    Account sourceAccount = filterAccountById(sourceAccountId, accounts);
    Account targetAccount = filterAccountById(targetAccountId, accounts);

    validateAccountAndBalance(sourceAccount, targetAccount, amount, currency);

    // make sure that the additions are done in the same currency
    BigDecimal sourceBalanceSubtrahend =
        CurrencyConversionUtil.convertCurrencyFromTo(currency, sourceAccount.getCurrency(), amount);

    BigDecimal targetBalanceAddend =
        CurrencyConversionUtil.convertCurrencyFromTo(currency, targetAccount.getCurrency(), amount);

    sourceAccount.setBalance(sourceAccount.getBalance().subtract(sourceBalanceSubtrahend));
    targetAccount.setBalance(targetAccount.getBalance().add(targetBalanceAddend));

    transferRepository.save(new Transfer(sourceAccountId, targetAccountId, amount, currency));
    accountRepository.save(sourceAccount);
    accountRepository.save(targetAccount);
  }

  private static Account filterAccountById(Integer accountId, List<Account> accounts) {
    return accounts.stream()
        .filter(account -> account.getAccountId().equals(accountId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Nonexistent source account"));
  }

  private void validateAccountAndBalance(
      Account sourceAccount, Account targetAccount, BigDecimal amount, String currency) {

    Assert.isTrue(
        !Objects.equals(sourceAccount.getAccountId(), targetAccount.getAccountId()),
        "Source and target account ids must be different");

    BigDecimal amountInDollars = CurrencyConversionUtil.convertToDollars(currency, amount);
    BigDecimal balanceInDollars =
        CurrencyConversionUtil.convertToDollars(
            sourceAccount.getCurrency(), sourceAccount.getBalance());

    Assert.isTrue(
        balanceInDollars.compareTo(amountInDollars) >= 0,
        "Insufficient balance in source account for transfer");
  }
}
