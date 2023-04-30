package com.agileactors.moneytransfer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dao.TransferRepository;
import com.agileactors.moneytransfer.model.Account;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransferServiceImplTest {

  private final TransferRepository transferRepository = mock(TransferRepository.class);
  private final AccountRepository accountRepository = mock(AccountRepository.class);
  private final TransferService transferService =
      new TransferServiceImpl(transferRepository, accountRepository);

  @Test
  void testTransferMoneyToSelf() {
    BigDecimal hundredDollars = BigDecimal.valueOf(100);
    assertThrows(
        IllegalArgumentException.class,
        () -> transferService.transferMoney(1, 1, hundredDollars, "USD"));
  }

  @Test
  void testTransferMoreMoneyThanBalance() {
    BigDecimal hundredDollars = BigDecimal.valueOf(100);
    when(accountRepository.findByAccountIds(List.of(1, 2)))
        .thenReturn(
            List.of(
                new Account(1, BigDecimal.valueOf(50), "USD"),
                new Account(2, BigDecimal.valueOf(50), "USD")));

    assertThrows(
        IllegalArgumentException.class,
        () -> transferService.transferMoney(1, 2, hundredDollars, "USD"));
  }

  @Test
  void testTransferBetweenDifferentCurrencies() {
    BigDecimal thousandYen = BigDecimal.valueOf(1000);
    Account account1 = new Account(1, BigDecimal.valueOf(500), "USD");
    Account account2 = new Account(2, BigDecimal.valueOf(500), "EUR");
    when(accountRepository.findByAccountIds(List.of(1, 2))).thenReturn(List.of(account1, account2));

    transferService.transferMoney(1, 2, thousandYen, "JPY");

    assertEquals(new BigDecimal("493.00"), account1.getBalance());
    assertEquals(new BigDecimal("506.34"), account2.getBalance());
  }
}
