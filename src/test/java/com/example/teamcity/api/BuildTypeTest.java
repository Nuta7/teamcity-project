package com.example.teamcity.api;

import com.example.teamcity.models.*;
import com.example.teamcity.requests.CheckedRequests;
import com.example.teamcity.requests.unchecked.UncheckedBase;
import com.example.teamcity.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.teamcity.enums.Endpoint.*;
import static com.example.teamcity.generators.TestDataGenerator.generate;


@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors[0].message",Matchers.equalTo("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var projectAdminRole = generate(Role.class,"PROJECT_ADMIN", "p:" + testData.getProject().getId());
        var roleAssignments = Roles.builder()
                .role(List.of(projectAdminRole))
                .build();

        var userWithRole = testData.getUser();
        userWithRole.setRoles(roleAssignments);
        superUserCheckRequests.<User>getRequest(USERS).create(userWithRole);

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(userWithRole));

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getProject().getId(), createdBuildType.getProject().getId(), "Project id is not correct");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        var project1 = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));
        var internalId = superUserCheckRequests.<Project>getRequest(PROJECTS).read(project1.getId() +"?fields=internalId");

        var projectAdminRole1 = generate(Role.class,"PROJECT_ADMIN", "p:" + project1.getId());
        var roleAssignments1 = Roles.builder()
                .role(List.of(projectAdminRole1))
                .build();
        var userWithRole1 = generate(User.class);
        userWithRole1.setRoles(roleAssignments1);
        superUserCheckRequests.<User>getRequest(USERS).create(userWithRole1);


        var project2 = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));
        var projectAdminRole2 = generate(Role.class,"PROJECT_ADMIN", "p:" + project2.getId());
        var roleAssignments2 = Roles.builder()
                .role(List.of(projectAdminRole2))
                .build();
        var userWithRole2 = generate(User.class);
        userWithRole2.setRoles(roleAssignments2);
        superUserCheckRequests.<User>getRequest(USERS).create(userWithRole2);

        var buildType = generate(BuildType.class);
        buildType.setProject(project1);

        new UncheckedBase(Specifications.authSpec(userWithRole2), BUILD_TYPES)
                .create(buildType)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body("errors[0].message",Matchers.equalTo("You do not have enough permissions to access project with internal id: %s".formatted(internalId)));
    }
}