package com.example.teamcity.ui;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.ProjectsPage;
import org.testng.annotations.Test;


import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

@Test(groups = {"Regression"})
public class SearchProjectByName extends BaseUiTest{

    @Test(description = "User should be able to find a project by its name", groups = {"Positive"})
    public void userSearchesProjectByName() {
        createTestUser();
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        loginAs(testData.getUser());
        var foundProject = ProjectsPage.searchProjectByName(testData.getProject().getName());
        softy.assertEquals(foundProject.title.text(), testData.getProject().getName());
    }
}
