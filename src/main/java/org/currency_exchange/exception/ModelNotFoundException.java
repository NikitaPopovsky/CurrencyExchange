package org.currency_exchange.exception;

public class ModelNotFoundException extends CurrencyExchangeException {
    public ModelNotFoundException(String message) {
        super(message, "404");
    }
}
