package org.currency_exchange.service;

import org.currency_exchange.db.CurrenciesDB;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.model.Currency;

import java.util.List;

public class CurrenciesService {
    private final CurrenciesDB currenciesDB;

    public CurrenciesService(CurrenciesDB currenciesDB) {
        this.currenciesDB = new CurrenciesDB();
    }

    public List<CurrencyDTO> getAllCurrencies () {
        List<Currency> Currencies= currenciesDB.findAll();
    }
}
