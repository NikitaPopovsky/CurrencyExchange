package org.currency_exchange.db;

import com.google.protobuf.DescriptorProtos;
import org.currency_exchange.mapper.CurrencyDAOMapper;
import org.currency_exchange.model.Currency;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        List<Currency> currencies = List.of();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT id, code, full_name, sign FROM currencies";
            ResultSet resultSet = statement.executeQuery(sql);
            currencies = mapper.toCurrencyList(resultSet);
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        }

        return currencies;

    }

    public Optional<Currency> findByCode (String code) {
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("SELECT id, code, full_name, sign FROM currencies WHERE code = '%s'", code);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Optional.of(mapper.toCurrency(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        }

        return Optional.empty();
    }

    public Optional<Currency> findById (int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("SELECT id, code, full_name, sign FROM currencies WHERE id = '%d'", id);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Optional.of(mapper.toCurrency(resultSet));

        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        }

        return Optional.empty();
    }

    public void save (Currency currency) {
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("insert into currencies (code, full_name, sign) values ('%s','%s','%s')"
                , currency.getCode(), currency.getFullName(), currency.getSign());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с БД");
        }
    }
}
