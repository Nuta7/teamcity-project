package com.example.teamcity.ui;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.EditBuildRunnersPage;
import com.example.teamcity.ui.pages.admin.EditRunTypePage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

public class StartBuildConfiguration extends BaseUiTest{
    @Test(description = "User should be able to create a build configuration", groups = {"Positive"})
    public void userCreatesBuild() {
        createTestUser();
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        loginAs(testData.getUser());

        BuildConfigurationPage.open(testData.getBuildType().getId()).clickSettingsButton().clickBuildStepsButton().addBuildStep();
        EditRunTypePage.open(testData.getBuildType().getId()).clickOnCommandLine();
        EditRunTypePage.open(testData.getBuildType().getId()).createBuildStep("Print Hello World", "echo 'Hello World'");
        var build = EditBuildRunnersPage.open(testData.getBuildType().getId()).runBuild();
        softy.assertEquals(build.clickBuildLogTab().checkLogs("Hello World"), "Hello World");

    }
}
