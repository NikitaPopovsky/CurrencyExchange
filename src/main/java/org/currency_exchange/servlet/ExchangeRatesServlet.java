package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.dto.ExchangeRateRequestDTO;
import org.currency_exchange.model.ExchangeRate;
import org.currency_exchange.service.CurrencyService;
import org.currency_exchange.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet ("/")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;
    private final ObjectMapper JSONMapper;

    public ExchangeRatesServlet() {
        this.exchangeRateService = new ExchangeRateService();
        this.JSONMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateService.getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),exchangeRateDTOList);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode  = req.getParameter("targetCurrencyCode");
        BigDecimal rate  = new BigDecimal(req.getParameter("rate"));

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.create(new ExchangeRateRequestDTO(baseCurrencyCode
                , targetCurrencyCode, rate));
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),exchangeRateDTO);
    }
}
