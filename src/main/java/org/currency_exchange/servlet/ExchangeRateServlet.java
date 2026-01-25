package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet ("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;
    private final ObjectMapper JSONMapper;

    public ExchangeRateServlet() {
        this.exchangeRateService = new ExchangeRateService();
        this.JSONMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codes = req.getPathInfo();
        ExchangeRateDTO exchangeRateDTO = exchangeRateService.getByPairCode(codes);
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),exchangeRateDTO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codes = req.getPathInfo();
        BigDecimal rate = new BigDecimal(req.getParameter("rate"));

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.updateRate(codes, rate);
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),exchangeRateDTO);
    }
}
