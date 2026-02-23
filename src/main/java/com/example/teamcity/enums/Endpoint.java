package com.example.teamcity.enums;

import com.example.teamcity.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECTS("/app/rest/projects", Project.class),
    USERS("/app/rest/users",User.class),
    BUILD_QUEUE("/app/rest/buildQueue",Build.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;
}
