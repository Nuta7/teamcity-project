package com.example.teamcity.ui.enums;

public enum SystemProjectId {
    ROOT("_Root");

    private final String id;

    SystemProjectId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
