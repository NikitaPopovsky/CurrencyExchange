package org.currency_exchange.dto;

import java.math.BigDecimal;

public record ExchangeRateRequestDTO (String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
}

