package com.dot.osore.auth.constant;

public enum OAuthPlatform {
    GITHUB("GITHUB", 0),
    GOOGLE("GOOGLE", 1);

    private String name;
    private int code;

    OAuthPlatform(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
