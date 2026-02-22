package org.currency_exchange.exception;

public abstract class CurrencyExchangeException extends RuntimeException {
    private final int statusCode;

    public CurrencyExchangeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
