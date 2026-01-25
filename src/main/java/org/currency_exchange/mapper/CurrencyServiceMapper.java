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
        return new CurrencyDTO(currency.id(), currency.code(), currency.fullName(), currency.sign());
    }

    public Currency toModel (CurrencyDTO currencyDTO) {
        return new Currency(currencyDTO.id(), currencyDTO.code(), currencyDTO.name(), currencyDTO.sign());
    }
}
