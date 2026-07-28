package com.example.teamcity.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ValidationResponseSpecifications {

    public static ResponseSpecification checkProjectAdminCantCreateBuildTypeForAnotherUserProject(String internalId) {
            ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
            responseSpecBuilder.expectStatusCode(HttpStatus.SC_FORBIDDEN);
        responseSpecBuilder.expectBody(Matchers.containsString("You do not have enough permissions to access project with internal id: %s".formatted(internalId)));
        return responseSpecBuilder.build();
        }

    public static ResponseSpecification checkUserCantCreateTwoBuildTypesWithTheSameId(String id) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder.expectBody(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(id)));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification checkUserCantCreateBuildTypeWithoutName(String id) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_NOT_FOUND);
        responseSpecBuilder.expectBody("errors[0].message",Matchers.equalTo("No build type nor template is found by id '%s'.".formatted(id)));
        return responseSpecBuilder.build();
    }

}

