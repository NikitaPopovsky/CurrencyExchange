package org.currency_exchange.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ResponseBuilderUtil {
    private static final ObjectMapper JSONMapper = new ObjectMapper();

    public static void writeResponse(HttpServletResponse resp, Object data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        JSONMapper.writeValue(resp.getWriter(), data);
    }
}
