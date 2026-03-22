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
    private final ExchangeRateDAOMapper mapper;


    public ExchangeRateDAO() {
        this.mapper = new ExchangeRateDAOMapper();
    }

    public List<ExchangeRate> findAll () {
        String sql = ExchangeRateSQL.FIND_ALL.getSql();
        try (Connection connection = UtilDAO.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            return mapper.toExchangeRateList(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public Optional<ExchangeRate> findByPairCodeCurrency(String baseCurrencyCode, String targetCurrencyCode) {
        String sql = ExchangeRateSQL.FIND_BY_CURRENCIES_CODE.getSql();
        try (Connection connection = UtilDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,baseCurrencyCode);
            statement.setString(2,targetCurrencyCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.toExchangeRate(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        String sql = ExchangeRateSQL.SAVE.getSql();
        try (Connection connection = UtilDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1,exchangeRate.baseCurrency().id());
            statement.setInt(2,exchangeRate.targetCurrency().id());
            statement.setBigDecimal(3,exchangeRate.rate());

            int countRecords = statement.executeUpdate();
            if (countRecords != 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int id = resultSet.getInt(1);
                return new ExchangeRate(id,exchangeRate.baseCurrency()
                        ,exchangeRate.targetCurrency(),exchangeRate.rate());
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public ExchangeRate updateRate (ExchangeRate exchangeRate, BigDecimal rate) {
        String sql = ExchangeRateSQL.UPDATE_RATE.getSql();
        try (Connection connection = UtilDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            statement.setBigDecimal(1,rate);
            statement.setInt(2,exchangeRate.id());

            int countRecords = statement.executeUpdate();
            if (countRecords != 0) {
                return new ExchangeRate(exchangeRate.id(),exchangeRate.baseCurrency()
                        ,exchangeRate.targetCurrency(),rate);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

}
