package org.currency_exchange.util;

import org.currency_exchange.exception.CodeIsMissing;

import java.math.BigDecimal;

public final class ValidationUtil {
    public static void validationCurrencyData(String name, String code, String sign) {
        validationCode(code);
        validationName(name);
        validationSign(sign);
    }

    public static void validationExchangeRateData(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        validationCode(baseCurrencyCode);
        validationCode(targetCurrencyCode);
        validationRate(rate);
    }

    public static void validationExchangeData(String baseCurrencyCode, String targetCurrencyCode, String amount) {
        validationCode(baseCurrencyCode);
        validationCode(targetCurrencyCode);
        validationAmount(amount);
    }

    public static void validationCode(String code) {
        if (code.isEmpty()) {
            throw new CodeIsMissing("Не указан код валюты");
        }
        if (code.length() != 3) {
            throw new CodeIsMissing("Длина кода валюты должна быть 3 символа");
        }
        if (!code.matches("[A-Z]+")) {
            throw new CodeIsMissing("Код должен содержать только латинские заглавные буквы");
        }
    }

    public static void validationName (String name) {
        if (name.isEmpty()) {
            throw new CodeIsMissing("Не указано имя валюты");
        }
        if (name.length() > 50) {
            throw new CodeIsMissing("Имя валюты не должно превышать 50 символов");
        }
        if (!name.matches("[a-zA-Z\\s]+")) {
            throw new CodeIsMissing("Имя валюты может содержать только латинские буквы");
        }
    }

    public static void validationSign (String sign) {
        if (sign.isEmpty()) {
            throw new CodeIsMissing("Не указан знак валюты");
        }
        if (sign.length() != 1) {
            throw new CodeIsMissing("Знак валюты должен содержать 1 символ");
        }
        if (!sign.matches("[A-Z\\p{Sc}]+")) {
            throw new CodeIsMissing("Знак валюты может состоять из заглавной латинской буквы или символа валюты");
        }
    }

    public static void validationRate (String stringRate) {
        validationNum(stringRate, "Курс валюты");

    }

    public static void validationAmount (String stringAmount) {
        validationNum(stringAmount, "Количество валюты");

    }

    public static void validationPairCode(String pairCode) {
        if (pairCode.isEmpty()) {
            throw new CodeIsMissing("Не указана пара кодов валюты");
        }
        if (pairCode.length() != 6) {
            throw new CodeIsMissing("Длина пары кодов валют должна быть 6 символов");
        }
        if (!pairCode.matches("[A-Z]+")) {
            throw new CodeIsMissing("Пара кодов должна содержать только латинские заглавные буквы");
        }
    }

    private static void validationNum (String stringNum, String fieldName) {
        if (!stringNum.matches("^-?\\d+(\\.\\d+)?$")) {
            throw new CodeIsMissing(String.format("%s может быть только числом", fieldName));
        }

        BigDecimal rate = new BigDecimal(stringNum);

        if (rate == null) {
            throw new CodeIsMissing(String.format("%s не указан", fieldName));
        }
        if (rate.compareTo(BigDecimal.ZERO) <= 0)  {
            throw new CodeIsMissing(String.format("%s должен быть больше нуля", fieldName));
        }

    }



}
