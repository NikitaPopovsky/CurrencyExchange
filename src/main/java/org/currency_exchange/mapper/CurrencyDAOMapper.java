package org.currency_exchange.mapper;

import org.currency_exchange.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CurrencyDAOMapper {

    public List<Currency> toCurrencyList (ResultSet resultSet) throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        while (resultSet.next()) {
            currencies.add(toCurrency(resultSet));
        }
        return currencies;
    }

    public Currency toCurrency (ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String code = resultSet.getString("code");
        String fullName = resultSet.getString("full_name");
        String sign = resultSet.getString("sign");

        return new Currency(id, code, fullName, sign);
    }
}
