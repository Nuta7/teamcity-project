package com.example.teamcity.api.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildStatus {
    SUCCESS ("SUCCESS"),
    UNKNOWN ("UNKNOWN");
    @JsonValue
    private final String value;
}
