package com.example.teamcity.api;

import com.example.teamcity.models.Build;
import com.example.teamcity.models.BuildType;
import com.example.teamcity.requests.CheckedRequests;
import com.example.teamcity.requests.checked.CheckedBase;
import com.example.teamcity.spec.Specifications;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static com.example.teamcity.enums.Endpoint.BUILD_QUEUE;


@Feature("Start build")
    public class StartBuildTest extends BaseMockTest {
        @Test(description = "User should be able to start build (with WireMock)", groups = {"Regression"})
        public void userStartsBuildWithWireMockTest() {
            this.setupBuildQueueStub("finished","SUCCESS");
            var checkedBuildQueueRequest = new CheckedBase<Build>(Specifications.mockSpec(), BUILD_QUEUE);

            var build = checkedBuildQueueRequest.create(Build.builder()
                    .buildType(testData.getBuildType())
                    .build());

            softy.assertEquals(build.getState(),"finished");
            softy.assertEquals(build.getStatus(), "SUCCESS");
        }


    @Test(description = "Build type should be started successfully with a echo 'Hello, world!'(with WireMock)", groups = {"Positive", "BuildType"})
    public void startBuildTypeWithAMessage(){
        var buildTypeId = testData.getBuildType().getId();
        this.setupBuildQueueStub(buildTypeId, "queued","UNKNOWN");

        var mockRequests = new CheckedRequests(Specifications.mockSpec());
        var buildResponse = mockRequests.<Build>getRequest(BUILD_QUEUE).create(
                Build.builder()
                        .buildType(BuildType.builder()
                                .id(buildTypeId)
                                .build())
                        .build()
        );

        var createdBuild = mockRequests.<Build>getRequest(BUILD_QUEUE).read(String.valueOf(buildResponse.getId()));

        softy.assertEquals(testData.getBuildType().getId(), createdBuild.getBuildTypeId());
        softy.assertEquals("queued", createdBuild.getState());
    }

    }

