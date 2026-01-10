package org.currency_exchange.enums;

public enum CurrencySQL {
    FIND_ALL ("SELECT id, code, full_name, sign FROM currencies"),
    FIND_BY_CODE ("SELECT id, code, full_name, sign FROM currencies WHERE code = ?"),
    FIND_BY_ID ("SELECT id, code, full_name, sign FROM currencies WHERE id = ?"),
    SAVE ("INSERT INTO currencies (code, full_name, sign) values (?,?,?)");



    private final String sql;

    CurrencySQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
