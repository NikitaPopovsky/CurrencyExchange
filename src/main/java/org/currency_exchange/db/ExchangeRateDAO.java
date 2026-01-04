package org.currency_exchange.db;

import org.currency_exchange.exception.CurrencyNotFoundException;
import org.currency_exchange.mapper.CurrencyDAOMapper;
import org.currency_exchange.mapper.ExchangeRateDAOMapper;
import org.currency_exchange.model.Currency;
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
        List<ExchangeRate> exchangeRates = List.of();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT id, base_currency_id, target_currency_id, rate FROM exchange_rates";
            ResultSet resultSet = statement.executeQuery(sql);
            exchangeRates = mapper.toExchangeRateList(resultSet);
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        } catch (CurrencyNotFoundException e) {
            throw new RuntimeException(e);
        }

        return exchangeRates;
    }

    public Optional<ExchangeRate> findByCurrenciesCode(String baseCurrencyCode, String targetCurrencyCode) {
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("SELECT ex.id, ex.base_currency_id, ex.target_currency_id, ex.rate " +
                    "FROM exchange_rates as ex " +
                    "JOIN currencies as base_currencies ON ex.base_currency_id = base_currencies.id " +
                    "JOIN currencies as target_currency ON ex.target_currency_id = target_currency.id " +
                    "WHERE base_currencies.code = %s " +
                    "AND target_currency.code = %s"
                    , baseCurrencyCode, targetCurrencyCode);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Optional.of(mapper.toExchangeRate(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        } catch (CurrencyNotFoundException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public void save(ExchangeRate exchangeRate) {
        try {
            String sql = "insert into exchange_rates (base_currency_id, target_currency_id, rate) values (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,exchangeRate.getBaseCurrency().getId());
            statement.setInt(1,exchangeRate.getTargetCurrency().getId());
            statement.setBigDecimal(1,exchangeRate.getRate());

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        }
    }

    public void updateRate (ExchangeRate exchangeRate, BigDecimal rate) {
        try {
            String sql = "UPDATE exchange_rates SET rate = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1,rate);
            statement.setInt(1,exchangeRate.getId());

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        }
    }

}
