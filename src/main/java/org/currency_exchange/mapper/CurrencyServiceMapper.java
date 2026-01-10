package org.currency_exchange.mapper;

import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.model.Currency;

import java.util.ArrayList;
import java.util.List;

public final class CurrencyServiceMapper {
    public List<CurrencyDTO> toDtoList (List<Currency> currencies) {
        List <CurrencyDTO> currencyDTOList = new ArrayList<>();
        for (Currency currency: currencies) {
            currencyDTOList.add(toDTO(currency));
        }
        return currencyDTOList;
    }

    public CurrencyDTO toDTO (Currency currency) {
        return new CurrencyDTO(0, currency.code(), currency.sign(), currency.fullName());
    }

    public Currency toModel (CurrencyDTO currencyDTO) {
        return new Currency(0, currencyDTO.code(), currencyDTO.sign(), currencyDTO.fullName());
    }
}
