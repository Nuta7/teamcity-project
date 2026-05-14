package com.example.teamcity.api.enums;

public enum LocatorType {
    ID("id:%s"),
    NAME("name:%s");

    private final String format;

    LocatorType(String format) {
        this.format = format;
    }

    public String format(Object value) {
        return String.format(this.format, value);
    }
}
