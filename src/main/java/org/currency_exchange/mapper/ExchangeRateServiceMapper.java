package org.currency_exchange.mapper;

import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.dto.ExchangeRateRequestDTO;
import org.currency_exchange.model.Currency;
import org.currency_exchange.model.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateServiceMapper {
    public List<ExchangeRateDTO> toDtoList(List<ExchangeRate> exchangeRates) {
        List <ExchangeRateDTO> exchangeRatesDTO = new ArrayList<>();
        for (ExchangeRate exchangeRate: exchangeRates) {
            exchangeRatesDTO.add(toDTO(exchangeRate));
        }
        return exchangeRatesDTO;
    }

    public ExchangeRateDTO toDTO (ExchangeRate exchangeRate) {
        return new ExchangeRateDTO(0, exchangeRate.baseCurrency(), exchangeRate.targetCurrency(),exchangeRate.rate());
    }

    public ExchangeRate toModel (ExchangeRateDTO exchangeRateDTO) {
        return new ExchangeRate(0,exchangeRateDTO.baseCurrency()
                , exchangeRateDTO.targetCurrency(), exchangeRateDTO.rate());
    }
}
