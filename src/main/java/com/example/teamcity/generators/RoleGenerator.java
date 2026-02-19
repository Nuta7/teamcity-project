package com.example.teamcity.generators;

import com.example.teamcity.models.Role;

public class RoleGenerator {
    public static Role generateProjectAdmin(String projectId){
        return Role.builder()
                .roleId("PROJECT_ADMIN")
                .scope("p:" + projectId)
                .build();
    }
}

