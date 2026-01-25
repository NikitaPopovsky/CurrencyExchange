package org.currency_exchange.mapper;

import org.currency_exchange.dto.ExchangeDTO;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.dto.ExchangeRateRequestDTO;
import org.currency_exchange.model.Currency;
import org.currency_exchange.model.Exchange;
import org.currency_exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateServiceMapper {
    private final CurrencyServiceMapper currencyMapper;

    public ExchangeRateServiceMapper() {
        this.currencyMapper = new CurrencyServiceMapper();
    }

    public List<ExchangeRateDTO> toDtoList(List<ExchangeRate> exchangeRates) {
        List <ExchangeRateDTO> exchangeRatesDTO = new ArrayList<>();
        for (ExchangeRate exchangeRate: exchangeRates) {
            exchangeRatesDTO.add(toDTO(exchangeRate));
        }
        return exchangeRatesDTO;
    }

    public ExchangeRateDTO toDTO (ExchangeRate exchangeRate) {
                return new ExchangeRateDTO(exchangeRate.id(), currencyMapper.toDTO(exchangeRate.baseCurrency()),
                        currencyMapper.toDTO(exchangeRate.targetCurrency()),exchangeRate.rate());
    }

    public ExchangeRate toModel (ExchangeRateDTO exchangeRateDTO) {
        return new ExchangeRate(exchangeRateDTO.id(),currencyMapper.toModel(exchangeRateDTO.baseCurrency())
                , currencyMapper.toModel(exchangeRateDTO.targetCurrency()), exchangeRateDTO.rate());
    }

    public ExchangeDTO toExchangeDTO (ExchangeRate exchangeRate, BigDecimal amount) {
        return new ExchangeDTO (currencyMapper.toDTO(exchangeRate.baseCurrency())
                , currencyMapper.toDTO(exchangeRate.targetCurrency())
                , exchangeRate.rate()
                , amount
                , exchangeRate.rate().multiply(amount));
    }
}
