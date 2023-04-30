package com.agileactors.moneytransfer.monetaryhelper;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConversionUtilTest {

  private static final BigDecimal aHundred =
      BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP);

  @Test
  void testConvertFromDollar() {
    assertEquals(
        new BigDecimal("90.51"), CurrencyConversionUtil.convertFromDollars("EUR", aHundred));
    assertEquals(
        new BigDecimal("79.58"), CurrencyConversionUtil.convertFromDollars("GBP", aHundred));
    assertEquals(
        new BigDecimal("13627.00"), CurrencyConversionUtil.convertFromDollars("JPY", aHundred));
    assertEquals(
        new BigDecimal("135.64"), CurrencyConversionUtil.convertFromDollars("CAD", aHundred));
  }

  @Test
  void testConvertToDollar() {
    assertEquals(new BigDecimal("110.49"), CurrencyConversionUtil.convertToDollars("EUR", aHundred));
    assertEquals(new BigDecimal("125.66"), CurrencyConversionUtil.convertToDollars("GBP", aHundred));
    assertEquals(new BigDecimal("0.73"), CurrencyConversionUtil.convertToDollars("JPY", aHundred));
    assertEquals(new BigDecimal("73.72"), CurrencyConversionUtil.convertToDollars("CAD", aHundred));
  }

  @Test
  void testConvertMissingCurrency() {
    assertThrows(
        IllegalArgumentException.class,
        () -> CurrencyConversionUtil.convertToDollars("AUD", aHundred));
  }
}
