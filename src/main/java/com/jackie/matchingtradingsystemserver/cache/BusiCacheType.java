package com.jackie.matchingtradingsystemserver.cache;

public enum BusiCacheType {

    CAPTCHA("captcha:"),

    ACCOUNT("account:"),

    ORDER("order:"),

    TRADE("trade:"),

    POSI("posi:"),
    ;

    private String type;

    BusiCacheType(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }


}
