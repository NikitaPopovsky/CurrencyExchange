package org.currency_exchange.db;

import org.currency_exchange.enums.CurrencySQL;
import org.currency_exchange.enums.ExceptionMessage;
import org.currency_exchange.exception.DataBaseUnavailable;
import org.currency_exchange.mapper.CurrencyDAOMapper;
import org.currency_exchange.model.Currency;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO {

    private final CurrencyDAOMapper mapper;

    public CurrencyDAO() {
        this.mapper = new CurrencyDAOMapper();
    }

    public List<Currency> findAll() {
        String sql = CurrencySQL.FIND_ALL.getSql();
        try (Connection connection = UtilDAO.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            return mapper.toCurrencyList(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public Optional<Currency> findByCode (String code) {
        String sql = CurrencySQL.FIND_BY_CODE.getSql();
        try (Connection connection = UtilDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,code);

            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return Optional.of(mapper.toCurrency(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public Optional<Currency> findById (int id) {
        String sql = CurrencySQL.FIND_BY_ID.getSql();
        try (Connection connection = UtilDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return Optional.of(mapper.toCurrency(resultSet));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }

    public Currency save (Currency currency) {
        String sql = CurrencySQL.SAVE.getSql();
        try (Connection connection = UtilDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS))   {

            String code = currency.code();
            String fullName = currency.fullName();
            String sign = currency.sign();

            statement.setString(1,code);
            statement.setString(2,fullName);
            statement.setString(3,sign);

            int countRecord = statement.executeUpdate();
            if (countRecord != 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int id = resultSet.getInt(1);

                return(new Currency(id,code,fullName,sign));
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }
}
