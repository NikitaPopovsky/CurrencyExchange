package org.currency_exchange;

public enum Config {
    PATH_CONFIG_PROPERTIES("src/main/resources/config.properties"),
    CONFIG_PROPERTIES("config.properties"),
    SCHEMA("schema.sql");

    private final String value;

    Config(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
