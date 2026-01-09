package org.currency_exchange.db;

import org.currency_exchange.enums.CurrencySQLQuery;
import org.currency_exchange.exception.DataBaseUnavailable;
import org.currency_exchange.mapper.CurrencyDAOMapper;
import org.currency_exchange.model.Currency;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO {

    private final Connection connection;
    private final CurrencyDAOMapper mapper;


    public CurrencyDAO() {
        this.connection = UtilDAO.getConnection();
        this.mapper = new CurrencyDAOMapper();
    }

    public List<Currency> findAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = CurrencySQLQuery.FIND_ALL.getSql();
            ResultSet resultSet = statement.executeQuery(sql);
            return mapper.toCurrencyList(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailable("Ошибка выполнения базы данных");
        }
    }

    public Optional<Currency> findByCode (String code) {
        try {
            String sql = CurrencySQLQuery.FIND_BY_CODE.getSql();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,code);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Optional.of(mapper.toCurrency(resultSet));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseUnavailable("Ошибка выполнения базы данных");
        }
    }

    public Optional<Currency> findById (int id) {
        try {
            String sql = CurrencySQLQuery.FIND_BY_CODE.getSql();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Optional.of(mapper.toCurrency(resultSet));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseUnavailable("Ошибка выполнения базы данных");
        }
    }

    public void save (Currency currency) {
        try {
            String sql = CurrencySQLQuery.SAVE.getSql();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,currency.getCode());
            statement.setString(1,currency.getFullName());
            statement.setString(1,currency.getSign());

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataBaseUnavailable("Ошибка выполнения базы данных");
        }
    }
}
