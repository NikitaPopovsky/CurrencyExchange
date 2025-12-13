package org.currency_exchange.db;

import org.currency_exchange.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UtilsDB {

    public static Connection getConnection(){
        Connection connection = null;
        Map <String, String> config = getConfig();

        if (config.isEmpty()) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection(config.get("url"), config.get("user"), config.get("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private static Map<String,String> getConfig() {
        Map <String, String> config = new HashMap<>();
        Properties properties = new Properties();
        FileInputStream fls;
        String url;
        String user;
        String password;

        try {
            fls = new FileInputStream(Config.PATH_CONFIG_PROPERTIES.getValue());
            properties.load(fls);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of();
        }

        config.put("url", url);
        config.put("user", user);
        config.put("password", password);
        return config;
    }
}
