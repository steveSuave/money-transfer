package com.agileactors.moneytransfer.monetaryhelper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CurrencyConversionUtilTest {

  private static final BigDecimal aHundred =
      BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP);

  @Test
  void testConvertFromDollar() {
    assertEquals(new BigDecimal("90.00"), CurrencyConversionUtil.convertFromDollars("EUR", aHundred));
    assertEquals(new BigDecimal("79.00"), CurrencyConversionUtil.convertFromDollars("GBP", aHundred));
    assertEquals(new BigDecimal("13627.00"), CurrencyConversionUtil.convertFromDollars("JPY", aHundred));
    assertEquals(new BigDecimal("135.00"), CurrencyConversionUtil.convertFromDollars("CAD", aHundred));
  }

  @Test
  void testConvertToDollar() {
    assertEquals(new BigDecimal("111.11"), CurrencyConversionUtil.convertToDollars("EUR", aHundred));
    assertEquals(new BigDecimal("126.58"), CurrencyConversionUtil.convertToDollars("GBP", aHundred));
    assertEquals(new BigDecimal("0.73"), CurrencyConversionUtil.convertToDollars("JPY", aHundred));
    assertEquals(new BigDecimal("74.07"), CurrencyConversionUtil.convertToDollars("CAD", aHundred));
  }

  @Test
  void testConvertCurrencyFromTo() {
    BigDecimal aHundredEuro =
        Optional.of(aHundred)
            .map(amnt -> CurrencyConversionUtil.convertCurrencyFromTo("EUR", "USD", amnt))
            .map(amnt -> CurrencyConversionUtil.convertCurrencyFromTo("USD", "JPY", amnt))
            .map(amnt -> CurrencyConversionUtil.convertCurrencyFromTo("JPY", "GBP", amnt))
            .map(amnt -> CurrencyConversionUtil.convertCurrencyFromTo("GBP", "CAD", amnt))
            .map(amnt -> CurrencyConversionUtil.convertCurrencyFromTo("CAD", "EUR", amnt))
            .orElseThrow();

    assertEquals(aHundred, aHundredEuro.setScale(2, RoundingMode.HALF_UP));
  }

  @Test
  void testConvertMissingCurrency() {
    assertThrows(
        IllegalArgumentException.class,
        () -> CurrencyConversionUtil.convertToDollars("AUD", aHundred));
  }
}
