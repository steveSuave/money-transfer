package com.agileactors.moneytransfer.service;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dao.TransferRepository;
import com.agileactors.moneytransfer.model.Account;
import com.agileactors.moneytransfer.model.Transfer;
import com.agileactors.moneytransfer.monetaryhelper.CurrencyConversionUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TransferServiceImpl implements TransferService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);

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
    validateAccountDistinction(sourceAccount, targetAccount);

    // make sure that the additions are done in the same currency
    BigDecimal targetBalanceAddend =
        CurrencyConversionUtil.convertCurrencyFromTo(currency, targetAccount.getCurrency(), amount);
    BigDecimal sourceBalanceSubtrahend =
        CurrencyConversionUtil.convertCurrencyFromTo(currency, sourceAccount.getCurrency(), amount);

    sourceAccount.subtractBalance(sourceBalanceSubtrahend);
    validateBalanceSufficiency(sourceAccount);
    targetAccount.addBalance(targetBalanceAddend);
    LOGGER.info(
        "Finalizing transfer of {} {} from account {} to account {}",
        amount,
        currency,
        sourceAccountId,
        targetAccountId);

    transferRepository.save(new Transfer(sourceAccountId, targetAccountId, amount, currency));
    accountRepository.save(sourceAccount);
    accountRepository.save(targetAccount);
  }

  private static Account filterAccountById(Integer accountId, List<Account> accounts) {
    return accounts.stream()
        .filter(account -> account.getAccountId().equals(accountId))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(String.format("Account %d not found", accountId)));
  }

  private void validateAccountDistinction(Account sourceAccount, Account targetAccount) {
    Assert.isTrue(
        !Objects.equals(sourceAccount.getAccountId(), targetAccount.getAccountId()),
        String.format("Source and target accounts are the same: %d", sourceAccount.getAccountId()));
  }

  private void validateBalanceSufficiency(Account sourceAccount) {
    Assert.isTrue(
        sourceAccount.getBalance().compareTo(BigDecimal.ZERO) >= 0,
        String.format("Insufficient funds in account %d", sourceAccount.getAccountId()));
  }

  @Override
  public Account getAccount(Integer accountId) {
    return accountRepository
            .findById(accountId)
            .orElseThrow(
                    () -> new IllegalArgumentException(String.format("Account %d not found", accountId)));
  }

}
