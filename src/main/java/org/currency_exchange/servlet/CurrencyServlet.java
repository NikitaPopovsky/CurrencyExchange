package org.currency_exchange.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.dto.CurrencyDTO;
import org.currency_exchange.service.CurrencyService;

import java.io.IOException;

@WebServlet ("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService;
    private final ObjectMapper JSONMapper;

    public CurrencyServlet() {
        this.currencyService = new CurrencyService();
        this.JSONMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getPathInfo();
        CurrencyDTO currencyDTO = currencyService.getByCode(code);

        resp.setStatus(HttpServletResponse.SC_OK);
        JSONMapper.writeValue(resp.getWriter(),currencyDTO);
    }
}
