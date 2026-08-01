package com.example.teamcity.api;

import com.example.teamcity.api.enums.BuildState;
import com.example.teamcity.api.generators.RoleGenerator;
import com.example.teamcity.api.generators.StepPropertyGenerator;
import com.example.teamcity.api.models.*;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.api.spec.ValidationResponseSpecifications;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;


@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("id:" + testData.getBuildType().getId());

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
                .then().spec(ValidationResponseSpecifications
                .checkUserCantCreateTwoBuildTypesWithTheSameId(testData.getBuildType().getId()));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var roleAssignments = Roles.builder()
                .role(List.of(RoleGenerator.generateProjectAdmin(testData.getProject().getId())))
                .build();

        var userWithRole = testData.getUser();
        userWithRole.setRoles(roleAssignments);
        superUserCheckRequests.<User>getRequest(USERS).create(userWithRole);

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(userWithRole));

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("id:" + testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getProject().getId(), createdBuildType.getProject().getId(), "Project id is not correct");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        var project1 = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));
        var roleAssignments1 = Roles.builder()
                .role(List.of(RoleGenerator.generateProjectAdmin(project1.getId())))
                .build();
        var userWithRole1 = generate(User.class);
        userWithRole1.setRoles(roleAssignments1);
        superUserCheckRequests.<User>getRequest(USERS).create(userWithRole1);


        var project2 = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));
        var roleAssignments2 = Roles.builder()
                .role(List.of(RoleGenerator.generateProjectAdmin(project2.getId())))
                .build();
        var userWithRole2 = generate(User.class);
        userWithRole2.setRoles(roleAssignments2);
        superUserCheckRequests.<User>getRequest(USERS).create(userWithRole2);

        var buildType = generate(BuildType.class);
        buildType.setProject(project1);

        UncheckedRequests.userRequest(userWithRole2)
                .getRequest(BUILD_TYPES)
                .create(buildType)
                .then().spec(ValidationResponseSpecifications
                .checkProjectAdminCantCreateBuildTypeForAnotherUserProject(project1.getId()));
    }

    @Test(description = "Build type should be started successfully with a echo 'Hello, world!'", groups = {"Positive", "BuildType"}, dependsOnGroups = {"SetupAgent"})
    public void startBuildTypeWithAMessage(){
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());


        var scriptProperties = StepPropertyGenerator.generateScriptProperties("echo 'Hello World!'");

        var step = Step.builder()
                .name("Print Hello World")
                .properties(scriptProperties)
                .build();

        var steps = Steps.builder()
                  .step(List.of(step))
                  .build();


        userCheckRequests.getRequest(BUILD_TYPES).create(generate(BuildType.class, testData.getBuildType().getId(), testData.getBuildType().getProject(), steps));

        var buildResponse = userCheckRequests.<Build>getRequest(BUILD_QUEUE).create(
                Build.builder()
                        .buildType(BuildType.builder()
                                .id(testData.getBuildType().getId())
                                .build())
                        .build()
        );
        Awaitility.await()
                .atMost(java.time.Duration.ofSeconds(60))
                .pollInterval(java.time.Duration.ofSeconds(5))
                .until(() -> {
                    var buildStateInfo = userCheckRequests.<Build>getRequest(BUILD_QUEUE).read("id:" + buildResponse.getId());
                    return buildStateInfo.getState() == BuildState.FINISHED;
                });

        var createdBuild = userCheckRequests.<Build>getRequest(BUILD_QUEUE).read("id:" + buildResponse.getId());

        softy.assertEquals(testData.getBuildType().getId(), createdBuild.getBuildTypeId());
        softy.assertEquals(BuildState.FINISHED, createdBuild.getState());
    }
}