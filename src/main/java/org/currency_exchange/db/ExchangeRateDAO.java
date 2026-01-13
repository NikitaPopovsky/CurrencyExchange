package org.currency_exchange.db;

import org.currency_exchange.enums.ExceptionMessage;
import org.currency_exchange.enums.ExchangeRateSQL;
import org.currency_exchange.exception.DataBaseUnavailable;
import org.currency_exchange.mapper.ExchangeRateDAOMapper;
import org.currency_exchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDAO {
    private final Connection connection;
    private final ExchangeRateDAOMapper mapper;


    public ExchangeRateDAO() {
        this.connection = UtilDAO.getConnection();
        this.mapper = new ExchangeRateDAOMapper();
    }

    public List<ExchangeRate> findAll () {
        try {
            Statement statement = connection.createStatement();
            String sql = ExchangeRateSQL.FIND_ALL.getSql();
            ResultSet resultSet = statement.executeQuery(sql);
            return mapper.toExchangeRateList(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public Optional<ExchangeRate> findByPairCodeCurrency(String baseCurrencyCode, String targetCurrencyCode) {
        try {
            String sql = ExchangeRateSQL.FIND_BY_CURRENCIES_CODE.getSql();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,baseCurrencyCode);
            statement.setString(2,targetCurrencyCode);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Optional.of(mapper.toExchangeRate(resultSet));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public void save(ExchangeRate exchangeRate) {
        try {
            String sql = ExchangeRateSQL.SAVE.getSql();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,exchangeRate.baseCurrency().id());
            statement.setInt(2,exchangeRate.targetCurrency().id());
            statement.setBigDecimal(3,exchangeRate.rate());

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public void updateRate (ExchangeRate exchangeRate, BigDecimal rate) {
        try {
            String sql = ExchangeRateSQL.UPDATE_RATE.getSql();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1,rate);
            statement.setInt(2,exchangeRate.id());

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

}
