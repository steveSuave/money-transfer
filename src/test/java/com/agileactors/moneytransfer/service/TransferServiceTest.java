package com.agileactors.moneytransfer.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dao.TransferRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TransferServiceTest {

  private final TransferRepository transferRepository = mock(TransferRepository.class);
  private final AccountRepository accountRepository = mock(AccountRepository.class);
  private final TransferService transferService =
      new TransferService(transferRepository, accountRepository);

  @Test
  void testTransferMoneyToSelf() {
    BigDecimal hundredDollars = BigDecimal.valueOf(100);
    assertThrows(
        IllegalArgumentException.class,
        () -> transferService.transferMoney(1, 1, hundredDollars, "USD"));
  }
}
