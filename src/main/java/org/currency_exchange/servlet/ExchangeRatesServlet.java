package org.currency_exchange.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.util.ResponseBuilderUtil;
import org.currency_exchange.util.ValidationUtil;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.dto.ExchangeRateRequestDTO;
import org.currency_exchange.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet ("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;

    public ExchangeRatesServlet() {
        this.exchangeRateService = new ExchangeRateService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateService.getAll();
        ResponseBuilderUtil.writeResponse(resp, exchangeRateDTOList);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode  = req.getParameter("targetCurrencyCode");
        String stringRate = req.getParameter("rate");

        ValidationUtil.validationExchangeRateData(baseCurrencyCode, targetCurrencyCode, stringRate);

        BigDecimal rate  = new BigDecimal(stringRate);

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.create(new ExchangeRateRequestDTO(baseCurrencyCode
                , targetCurrencyCode, rate));
        ResponseBuilderUtil.writeResponse(resp, exchangeRateDTO);
    }
}
