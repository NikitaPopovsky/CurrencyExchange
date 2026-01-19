package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.service.CurrencyService;

import java.io.IOException;
import java.util.List;

public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService;
    private final ObjectMapper JSONMapper;

    public CurrenciesServlet() {
        this.currencyService = new CurrencyService();
        this.JSONMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<CurrencyDTO> currencies = currencyService.getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),currencies);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
