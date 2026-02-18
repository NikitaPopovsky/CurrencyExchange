package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.dto.ExchangeDTO;
import org.currency_exchange.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;
    private final ObjectMapper JSONMapper;

    public ExchangeServlet() {
        this.exchangeRateService = new ExchangeRateService();
        this.JSONMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        ExchangeDTO exchangeDTO = exchangeRateService.exchange(from, to, amount);
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),exchangeDTO);
    }
}
