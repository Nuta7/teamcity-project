package com.example.teamcity.api.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildState {
    FINISHED ("finished"),
    QUEUED ("queued");
    @JsonValue
    private final String value;
}
