package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Parameterizable;
import com.example.teamcity.api.annotations.Random;
import com.example.teamcity.api.enums.BuildState;
import com.example.teamcity.api.enums.BuildStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Build extends BaseModel {
    @Random
    private long id;
    @Parameterizable
    private BuildStatus status;
    @Parameterizable
    private BuildState state;
    private BuildType buildType;
    private String buildTypeId;
}
