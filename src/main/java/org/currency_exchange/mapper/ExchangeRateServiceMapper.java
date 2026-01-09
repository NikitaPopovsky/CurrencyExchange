package org.currency_exchange.mapper;

import org.currency_exchange.dto.ExchangeRateDTO;
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
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateDTO.setRate(exchangeRate.getRate());

        return exchangeRateDTO;
    }

    public ExchangeRate toModel (ExchangeRateDTO exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(exchangeRateDTO.getBaseCurrency());
        exchangeRate.setTargetCurrency(exchangeRateDTO.getTargetCurrency());
        exchangeRate.setRate(exchangeRateDTO.getRate());

        return exchangeRate;
    }
}
