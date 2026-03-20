package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.util.ResponseBuilderUtil;
import org.currency_exchange.util.ValidationUtil;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.service.CurrencyService;

import java.io.IOException;
import java.util.List;

@WebServlet ("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService;

    public CurrenciesServlet() {
        this.currencyService = new CurrencyService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyDTO> currencies = currencyService.getAll();
        ResponseBuilderUtil.writeResponse(resp, currencies);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        ValidationUtil.validationCurrencyData(name, code, sign);

        CurrencyDTO currencyDTO = currencyService.create(new CurrencyDTO(0,code, name, sign));
        ResponseBuilderUtil.writeResponse(resp, currencyDTO);
    }
}
