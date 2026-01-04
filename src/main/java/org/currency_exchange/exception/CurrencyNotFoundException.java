package org.currency_exchange.exception;

public class CurrencyNotFoundException extends Exception {
    private String statusCode;

    public CurrencyNotFoundException() {
        super("Валюта не найдена");
        statusCode = "404";
    }
}
