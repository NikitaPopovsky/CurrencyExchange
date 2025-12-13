package org.currency_exchange;

public enum Config {
    PATH_CONFIG_PROPERTIES("src/main/resources/config.properties");
    private final String value;

    Config(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
