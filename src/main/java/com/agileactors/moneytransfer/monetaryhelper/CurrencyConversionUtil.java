package com.agileactors.moneytransfer.monetaryhelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencyConversionUtil {

  private CurrencyConversionUtil() {}

  static final Map<String, BigDecimal> exchangeRates = new HashMap<>();

  static {
    exchangeRates.put("USD", BigDecimal.ONE);
    exchangeRates.put("EUR", new BigDecimal("0.90509"));
    exchangeRates.put("GBP", new BigDecimal("0.79577"));
    exchangeRates.put("JPY", new BigDecimal("136.27"));
    exchangeRates.put("CAD", new BigDecimal("1.3564"));
  }

  public static BigDecimal convertFromDollars(String currencyCode, BigDecimal amount) {
    BigDecimal rate = getRate(currencyCode);
    return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
  }

  public static BigDecimal convertToDollars(String currencyCode, BigDecimal amount) {
    BigDecimal rate = getRate(currencyCode);
    return amount.divide(rate, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
  }

  public static BigDecimal convertCurrencyFromTo(
      String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
    return Optional.ofNullable(amount)
        .map(quantity -> convertToDollars(sourceCurrencyCode, quantity))
        .map(quantity -> convertFromDollars(targetCurrencyCode, quantity))
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format(
                        "Could not convert among currencies: from %s to %s",
                        sourceCurrencyCode, targetCurrencyCode)));
  }

  private static BigDecimal getRate(String currencyCode) {
    return Optional.ofNullable(currencyCode)
        .map(exchangeRates::get)
        .orElseThrow(
            () ->
                new IllegalArgumentException(String.format("Unknown currency: %s", currencyCode)));
  }
}
