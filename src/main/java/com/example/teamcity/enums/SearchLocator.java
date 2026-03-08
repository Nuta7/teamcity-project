package com.example.teamcity.enums;

public enum SearchLocator {
    NAME("name");

    private final String key;

    SearchLocator(String key) {
        this.key = key;
    }

    public String build(Object value) {
        return String.format("%s:%s", this.key, value);
    }
}
