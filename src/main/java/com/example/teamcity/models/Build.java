package com.example.teamcity.models;

import com.example.teamcity.annotations.Parameterizable;
import com.example.teamcity.annotations.Random;
import com.example.teamcity.enums.BuildState;
import com.example.teamcity.enums.BuildStatus;
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
