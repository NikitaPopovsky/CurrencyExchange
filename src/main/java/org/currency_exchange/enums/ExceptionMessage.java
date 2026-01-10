package org.currency_exchange.enums;

public enum ExceptionMessage {
    DB_NOT_UNAVAILABLE ("Ошибка выполнения базы данных");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
