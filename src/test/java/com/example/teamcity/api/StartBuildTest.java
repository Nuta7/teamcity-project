package com.example.teamcity.api;

import com.example.teamcity.api.enums.BuildState;
import com.example.teamcity.api.enums.BuildStatus;
import com.example.teamcity.api.models.Build;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_QUEUE;


@Feature("Start build")
    public class StartBuildTest extends BaseMockTest {

    @Test(description = "User should be able to start build (with WireMock)", groups = {"Regression"})
        public void userStartsBuildWithWireMockTest() {
        this.setupBuildQueueStub(BuildState.FINISHED, BuildStatus.SUCCESS);
            var checkedBuildQueueRequest = new CheckedBase<Build>(Specifications.mockSpec(), BUILD_QUEUE);

            var build = checkedBuildQueueRequest.create(Build.builder()
                    .buildType(testData.getBuildType())
                    .build());

            softy.assertEquals(build.getState(), BuildState.FINISHED);
            softy.assertEquals(build.getStatus(), BuildStatus.SUCCESS);
        }


    @Test(description = "Build type should be started successfully with a echo 'Hello, world!'(with WireMock)", groups = {"Positive", "BuildType"})
    public void startBuildTypeWithAMessage(){
        var buildTypeId = testData.getBuildType().getId();
        this.setupBuildQueueStub(buildTypeId, BuildState.QUEUED, BuildStatus.UNKNOWN);

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
        softy.assertEquals(BuildState.QUEUED, createdBuild.getState());
    }

    }

