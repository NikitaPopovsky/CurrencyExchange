package org.currency_exchange.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.service.CurrenciesService;

import java.io.IOException;
import java.util.List;

public class CurrenciesServlet extends HttpServlet {
    private final CurrenciesService currenciesService;

    public CurrenciesServlet(CurrenciesService currenciesService) {
        this.currenciesService = new CurrenciesService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<CurrencyDTO> currencies = currenciesService.getAllCurrencies();
        //String json = CurrenciesService
        //resp.getWriter().write(json);
    }
}
