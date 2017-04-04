package ru.igrey.dev;

/**
 * Created by sanasov on 01.04.2017.
 */
public enum CountryIsoCode {
    RUSSIA("RUS"),
    USA("USA");

    CountryIsoCode(String code) {
        this.code = code;
    }

    String code;

    public String getCode() {
        return code;
    }
}
