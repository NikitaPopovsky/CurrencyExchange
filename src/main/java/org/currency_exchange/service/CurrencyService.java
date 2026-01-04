package org.currency_exchange.service;

import org.currency_exchange.db.CurrencyDAO;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.mapper.CurrencyServiceMapper;
import org.currency_exchange.model.Currency;

import java.util.List;

public class CurrencyService {
    private final CurrencyDAO DAO;
    private final CurrencyServiceMapper mapper;

    public CurrencyService() {
        this.DAO = new CurrencyDAO();
        this.mapper = new CurrencyServiceMapper();
    }

    public List<CurrencyDTO> getAllCurrencies () {
        List<Currency> currencies = DAO.findAll();
        return mapper.toDtoList(currencies);
    }
}
