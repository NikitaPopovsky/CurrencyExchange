package org.currency_exchange.model;

import org.currency_exchange.dto.CurrencyDTO;

import java.math.BigDecimal;
import java.util.Currency;

public record Exchange (Currency baseCurrency,
                        Currency targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
}
