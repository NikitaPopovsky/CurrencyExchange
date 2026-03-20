package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.util.ResponseBuilderUtil;
import org.currency_exchange.util.ValidationUtil;
import org.currency_exchange.dto.ExchangeDTO;
import org.currency_exchange.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;

    public ExchangeServlet() {
        this.exchangeRateService = new ExchangeRateService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String stringAmount = req.getParameter("amount");

        ValidationUtil.validationExchangeData(from, to, stringAmount);

        BigDecimal amount = new BigDecimal(stringAmount);

        ExchangeDTO exchangeDTO = exchangeRateService.exchange(from, to, amount);
        ResponseBuilderUtil.writeResponse(resp, exchangeDTO);
    }
}
