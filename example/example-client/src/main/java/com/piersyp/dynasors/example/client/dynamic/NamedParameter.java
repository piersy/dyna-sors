package com.piersyp.dynasors.example.client.dynamic;

class NamedParameter {
    private final String name;
    private final int cookiePosition;

    public NamedParameter(String name, int cookiePosition) {
        this.name = name;
        this.cookiePosition = cookiePosition;
    }

    public String getName() {
        return name;
    }

    public int getCookiePosition() {
        return cookiePosition;
    }
}
