package com.agileactors.moneytransfer.monetaryhelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConversionUtil {

  static final Map<String, BigDecimal> exchangeRates = new HashMap<>();

  static {
    exchangeRates.put("USD", BigDecimal.ONE);
    exchangeRates.put("EUR", new BigDecimal("0.90509"));
    exchangeRates.put("GBP", new BigDecimal("0.79577"));
    exchangeRates.put("JPY", new BigDecimal("136.27"));
    exchangeRates.put("CAD", new BigDecimal("1.3564"));
  }

  public static BigDecimal convertFromDollar(String currencyCode, BigDecimal amount) {
    BigDecimal rate = getRate(currencyCode);
    return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
  }

  public static BigDecimal convertToDollar(String currencyCode, BigDecimal amount) {
    BigDecimal rate = getRate(currencyCode);
    return amount.divide(rate, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
  }

  private static BigDecimal getRate(String currencyCode) {
    BigDecimal rate = exchangeRates.get(currencyCode);
    if (rate == null) {
      throw new IllegalArgumentException("Unknown currency: " + currencyCode);
    }
    return rate;
  }


}
