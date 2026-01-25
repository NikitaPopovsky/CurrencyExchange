package org.currency_exchange;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.currency_exchange.db.UtilDAO;
import org.currency_exchange.enums.ExceptionMessage;
import org.currency_exchange.exception.DataBaseUnavailable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

@WebListener
public class DatabaseInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection connection = UtilDAO.getConnection();

            InputStream schema = getClass().getClassLoader().getResourceAsStream(Config.SCHEMA.getValue());
            assert schema != null;
            String sql = new String(schema.readAllBytes(), StandardCharsets.UTF_8);

            String[] commands = sql.split(";");
            assert connection != null;
            Statement statement = connection.createStatement();

            for(String command: commands) {
                if (!command.trim().isEmpty()) {
                    try {
                        statement.execute(command);
                    } catch (SQLIntegrityConstraintViolationException e) {
                        //Пропускаем ошибку при создании не уникальных валют
                    }

                }
            }

            statement.close();
            connection.close();

        } catch (SQLException | IOException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return;
            }
            throw new DataBaseUnavailable(ExceptionMessage.DB_NOT_UNAVAILABLE.getMessage());
        }
    }
}
