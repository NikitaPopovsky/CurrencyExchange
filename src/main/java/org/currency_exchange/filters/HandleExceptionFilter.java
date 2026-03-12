package org.currency_exchange.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.currency_exchange.exception.CurrencyExchangeException;

import java.io.IOException;

@WebFilter("/*")
public class HandleExceptionFilter implements Filter {
    private final ObjectMapper JSONMapper;

    public HandleExceptionFilter() {
        this.JSONMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (CurrencyExchangeException e) {
            handleException(e, response);
        }
    }


    private void handleException(CurrencyExchangeException exception, HttpServletResponse response) throws IOException {
        response.setStatus(exception.getStatusCode());
        JSONMapper.writeValue(response.getWriter(),exception);
    }
}
