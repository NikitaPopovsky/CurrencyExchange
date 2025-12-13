package org.currency_exchange.db;

import org.currency_exchange.model.Currency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class CurrenciesDB {

    public CurrenciesDB() {
    }

    public List<Currency> findAll() {
        Connection connection = UtilsDB.getConnection();

    }
}
