package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.api.spec.ValidationResponseSpecifications;
import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;

@Test(groups = {"Regression"})

public class CreateBuildConfigurationTest extends BaseUiTest {

    private static final String REPO_URL = "https://github.com/Nuta7/teamcity-project";
    private static final String DEFAULT_BRANCH = "refs/heads/main";

    @Test(description = "User should be able to create a build configuration", groups = {"Positive"})
    public void userCreatesBuild() {
        createTestUser();
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        loginAs(testData.getUser());

        CreateBuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(testData.getBuildType().getName(), DEFAULT_BRANCH);

        var expectedBuildId = CreateBuildConfigurationPage.transformId(createdProject.getId(), testData.getBuildType().getName());

        var createdBuildConfiguration = userCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("id:" + expectedBuildId);
        softy.assertNotNull(createdBuildConfiguration);

        BuildConfigurationPage.open(createdBuildConfiguration.getId())
                .title.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }


    @Test(description = "User should not be able to create a build configuration without a name", groups = {"Negative"})
    public void buildCreationWithoutName() {
        createTestUser();
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        loginAs(testData.getUser());

        CreateBuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(null, DEFAULT_BRANCH);

        var expectedBuildId = CreateBuildConfigurationPage.transformId(createdProject.getId(), testData.getBuildType().getName());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .read("id:" + expectedBuildId)
                .then().spec(ValidationResponseSpecifications.checkUserCantCreateBuildTypeWithoutName(expectedBuildId));
    }


}
