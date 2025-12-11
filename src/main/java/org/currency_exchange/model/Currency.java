package org.currency_exchange.model;

public class Currency {
    private int id;
    private int code;
    private int fullName;
    private int sign;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFullName() {
        return fullName;
    }

    public void setFullName(int full_name) {
        this.fullName = full_name;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
