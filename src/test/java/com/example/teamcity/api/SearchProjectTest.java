package com.example.teamcity.api;


import com.example.teamcity.api.enums.SearchLocator;
import com.example.teamcity.api.models.Projects;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;

@Test(groups = {"Regression"})
public class SearchProjectTest extends BaseApiTest {
   @Test (description = "User should be able to find a project by its name", groups = {"Positive", "Project"})
   public void userSearchProjectByNameTest() {
       superUserCheckRequests.getRequest(USERS).create(testData.getUser());
       var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

       var projectData = testData.getProject();
       userCheckRequests.getRequest(PROJECTS).create(projectData);

       var foundProjects = userCheckRequests.getRequest(PROJECTS).search(SearchLocator.NAME, projectData.getName(), Projects.class);

       softy.assertEquals(foundProjects.getProject().get(0).getName(), projectData.getName());
   }
}
