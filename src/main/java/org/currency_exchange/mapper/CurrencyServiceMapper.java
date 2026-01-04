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

    private CurrencyDTO toDTO (Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCode(currency.getCode());
        currencyDTO.setSign(currency.getSign());
        currencyDTO.setFullName(currency.getFullName());

        return currencyDTO;
    }
}
