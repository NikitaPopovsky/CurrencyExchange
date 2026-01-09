package org.currency_exchange.exception;

public class CodeIsMissing extends CurrencyExchangeException {
    public CodeIsMissing(String message) {
        super(message, "400");
    }

}
