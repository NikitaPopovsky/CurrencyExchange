package org.currency_exchange.enums;

public enum ExchangeRateSQL {
    FIND_ALL ("SELECT id, base_currency_id, target_currency_id, rate FROM exchange_rates"),
    FIND_BY_CURRENCIES_CODE ("SELECT ex.id, ex.base_currency_id, ex.target_currency_id, ex.rate " +
            "FROM exchange_rates as ex " +
            "JOIN currencies as base_currencies ON ex.base_currency_id = base_currencies.id " +
            "JOIN currencies as target_currency ON ex.target_currency_id = target_currency.id " +
            "WHERE base_currencies.code = ?" +
            "AND target_currency.code = ?"),
    SAVE ("insert into exchange_rates (base_currency_id, target_currency_id, rate) values (?,?,?)"),
    UPDATE_RATE ("UPDATE exchange_rates SET rate = ? WHERE id = ?");

    private final String sql;

    ExchangeRateSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
