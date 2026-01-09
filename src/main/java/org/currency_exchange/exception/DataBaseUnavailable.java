package org.currency_exchange.exception;

public class DataBaseUnavailable extends CurrencyExchangeException {
    public DataBaseUnavailable(String message) {
        super(message, "500");
    }
}
