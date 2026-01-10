package org.currency_exchange.exception;

public class ObjectAlreadyExists extends CurrencyExchangeException {
    public ObjectAlreadyExists(String message) {
        super(message, "409");
    }
}
