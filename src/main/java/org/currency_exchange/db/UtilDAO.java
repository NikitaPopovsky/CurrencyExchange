package org.currency_exchange.db;

import org.currency_exchange.Config;
import org.currency_exchange.exception.DataBaseUnavailable;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UtilDAO {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataBaseUnavailable("Ошибка драйвера подключения к БД");
        }

    }
    public static Connection getConnection(){
        Connection connection = null;
        Map <String, String> config = getConfig();

        if (config.isEmpty()) {
            return connection;
        }

        try {
            //Class.forName("mysql.Driver");
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(config.get("url"), config.get("user"), config.get("password"));
            if (connection == null) {
                throw new DataBaseUnavailable("Ошибка получения соединения с БД");
            }
        } catch (SQLException e) {
            throw new DataBaseUnavailable("Ошибка получения соединения с БД");
        }

        return connection;
    }

    private static Map<String,String> getConfig() {
        Map <String, String> config = new HashMap<>();
        Properties properties = new Properties();
        String url;
        String user;
        String password;

        try {
            InputStream inputStream = UtilDAO.class.getClassLoader().getResourceAsStream(Config.CONFIG_PROPERTIES.getValue());
            properties.load(inputStream);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new DataBaseUnavailable("Ошибка получения конфигурационных данных");
        }

        config.put("url", url);
        config.put("user", user);
        config.put("password", password);
        return config;
    }
}
