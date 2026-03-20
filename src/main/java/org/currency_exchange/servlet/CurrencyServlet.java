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

@WebServlet ("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService;

    public CurrencyServlet() {
        this.currencyService = new CurrencyService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getPathInfo().replace("/","");

        ValidationUtil.validationCode(code);

        CurrencyDTO currencyDTO = currencyService.getByCode(code);

        ResponseBuilderUtil.writeResponse(resp, currencyDTO);
    }
}
