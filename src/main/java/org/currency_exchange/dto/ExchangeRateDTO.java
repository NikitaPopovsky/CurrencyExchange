package org.currency_exchange.dto;

import org.currency_exchange.model.Currency;

import java.math.BigDecimal;

public record ExchangeRateDTO (int id, CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, BigDecimal rate) {
}
