package org.currency_exchange.exception;

public abstract class CurrencyExchangeException extends RuntimeException {
    private final String statusCode;

    public CurrencyExchangeException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
