package org.currency_exchange.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.currency_exchange.Config;
import org.currency_exchange.exception.DataBaseUnavailable;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class UtilDAO {
    private static final HikariDataSource dataSource;

    static {
        try {
            Class.forName(Config.DRIVER_BD.getValue());
        } catch (ClassNotFoundException e) {
            throw new DataBaseUnavailable("Ошибка драйвера подключения к БД");
        }

        try {
            dataSource = new HikariDataSource(getConfig());
        } catch (Exception e) {
            throw new DataBaseUnavailable("Ошибка инициализации пула подключений: " + e.getMessage());
        }

    }
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataBaseUnavailable("Ошибка получения соединения с БД");
        }
    }

    private static HikariConfig getConfig() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = UtilDAO.class.getClassLoader().getResourceAsStream(Config.CONFIG_PROPERTIES.getValue());
            properties.load(inputStream);
        } catch (IOException e) {
            throw new DataBaseUnavailable("Ошибка получения конфигурационных данных");
        }
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(30000);
        config.setLeakDetectionThreshold(60000);

        return config;
    }
}
