package org.currency_exchange.dto;

import org.currency_exchange.model.Currency;

import java.math.BigDecimal;

public class ExchangeRateDTO {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
