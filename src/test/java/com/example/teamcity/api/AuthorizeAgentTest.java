package com.example.teamcity.api;


import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Agents;
import com.example.teamcity.api.models.AuthorizedInfo;
import com.example.teamcity.api.models.Comment;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.AGENTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;

public class AuthorizeAgentTest extends BaseApiTest {
    @Test(description = "User should be able to authorize an agent", groups = {"Positive", "SetupAgent", "Regression"})
    public void authorizeAgentTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        Agents agentsCollection = (Agents) userCheckRequests.getRequest(AGENTS).search("authorized:false");
        int agentId = agentsCollection.getAgent().get(0).getId();

        var authInfo = AuthorizedInfo.builder()
                .value(true)
                .comment(Comment.builder().text("Authorized via API automation test").build())
                .build();

        UncheckedRequests.userRequest(testData.getUser())
                .getRequest(AGENTS)
                .update("id:" + agentId + "/authorizedInfo", authInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK);

        boolean isAuthorized = UncheckedRequests.userRequest(testData.getUser())
                .getRequest(Endpoint.AGENTS)
                .read("id:" + agentId)
                .path("authorized");

        softy.assertTrue(isAuthorized, "Статус авторизации агента должен быть true");
    }
}
