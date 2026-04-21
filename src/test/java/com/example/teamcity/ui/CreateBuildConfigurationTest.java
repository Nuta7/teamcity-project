package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

@Test(groups = {"Regression"})

public class CreateBuildConfigurationTest extends BaseUiTest {

    private static final String REPO_URL = "https://github.com/Nuta7/teamcity-project";
    private static final String DEFAULT_BRANCH = "refs/heads/main";

    @Test(description = "User should be able to create a build configuration", groups = {"Positive"})
    public void userCreatesBuild() {
        var createdProject = superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        loginAs(testData.getUser());

        CreateBuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(testData.getBuildType().getName(), DEFAULT_BRANCH);

        var createdBuildConfiguration = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("/id:" + testData.getBuildType().getId());
        softy.assertNotNull(createdBuildConfiguration);

        BuildConfigurationPage.open(createdBuildConfiguration.getId())
                .title.shouldHave(Condition.exactText(testData.getBuildType().getId()));
    }


    @Test(description = "User should not be able to create a build configuration without a name", groups = {"Negative"})
    public void buildCreationWithoutName() {

    }


}
