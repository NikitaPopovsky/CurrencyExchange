package org.currency_exchange.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.db.CurrencyDAO;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.service.CurrencyService;

import java.io.IOException;
import java.util.List;

public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService;

    public CurrenciesServlet() {
        this.currencyService = new CurrencyService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<CurrencyDTO> currencies = currencyService.getAllCurrencies();
    }
}
