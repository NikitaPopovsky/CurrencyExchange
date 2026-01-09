package org.currency_exchange.mapper;

import org.currency_exchange.db.CurrencyDAO;
import org.currency_exchange.exception.ModelNotFoundException;
import org.currency_exchange.model.Currency;
import org.currency_exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDAOMapper {
    public List<ExchangeRate> toExchangeRateList (ResultSet resultSet) throws SQLException, ModelNotFoundException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        while (resultSet.next()) {
            exchangeRates.add(toExchangeRate(resultSet));
        }
        return exchangeRates;
    }

    public ExchangeRate toExchangeRate (ResultSet resultSet) throws SQLException, ModelNotFoundException {
        int id = resultSet.getInt("id");
        int baseCurrencyId = resultSet.getInt("base_currency_id");
        int targetCurrencyId = resultSet.getInt("target_currency_id");
        BigDecimal rate = resultSet.getBigDecimal("rate");

        CurrencyDAO currencyDAO = new CurrencyDAO();
        Optional<Currency> baseCurrencyOptional = currencyDAO.findById(baseCurrencyId);
        Optional<Currency> targetCurrencyOptional = currencyDAO.findById(targetCurrencyId);

        if (baseCurrencyOptional.isEmpty() || targetCurrencyOptional.isEmpty()) {
            throw new ModelNotFoundException("Валюта не найдена");
        }
        return new ExchangeRate(id, baseCurrencyOptional.get(), targetCurrencyOptional.get(), rate);


    }
}
