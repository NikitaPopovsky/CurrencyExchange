package org.currency_exchange.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.util.ResponseBuilderUtil;
import org.currency_exchange.util.ValidationUtil;
import org.currency_exchange.dto.ExchangeRateDTO;
import org.currency_exchange.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@WebServlet ("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateServlet() {
        this.exchangeRateService = new ExchangeRateService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String codes = req.getPathInfo().replace("/","");

        ValidationUtil.validationPairCode(codes);

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.getByPairCode(codes);
        ResponseBuilderUtil.writeResponse(resp, exchangeRateDTO);
    }


    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String codes = req.getPathInfo().replace("/","");
        String body = req.getReader().lines().collect(Collectors.joining());
        String stringRate = body.replace("rate=", "");

        ValidationUtil.validationPairCode(codes);
        ValidationUtil.validationRate(stringRate);

        BigDecimal rate = new BigDecimal(stringRate);

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.updateRate(codes, rate);
        ResponseBuilderUtil.writeResponse(resp, exchangeRateDTO);
    }
}
