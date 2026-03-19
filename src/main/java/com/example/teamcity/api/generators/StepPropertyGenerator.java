package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.Properties;
import com.example.teamcity.api.models.Property;

import java.util.List;

public class StepPropertyGenerator {
    public static Properties generateScriptProperties(String scriptContent) {
        return Properties.builder()
                .property(List.of(
                        Property.builder()
                                .name("script.content")
                                .value(scriptContent)
                                .build(),
                        Property.builder()
                                .name("use.custom.script")
                                .value("true")
                                .build()
                ))
                .build();
    }

}
