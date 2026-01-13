package org.currency_exchange.dto;

import java.math.BigDecimal;

public record ExchangeDTO (CurrencyDTO baseCurrency,
                           CurrencyDTO targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
}
