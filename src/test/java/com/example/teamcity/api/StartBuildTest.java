package com.example.teamcity.api;

import com.example.teamcity.enums.BuildState;
import com.example.teamcity.enums.BuildStatus;
import com.example.teamcity.models.Build;
import com.example.teamcity.requests.checked.CheckedBase;
import com.example.teamcity.spec.Specifications;
import com.example.teamcity.common.WireMock;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.example.teamcity.enums.Endpoint.BUILD_QUEUE;


    @Feature("Start build")
    public class StartBuildTest extends BaseApiTest {

        @BeforeMethod
        public void setupWireMockServer() {
            var fakeBuild = Build.builder()
                    .state(BuildState.FINISHED)
                    .status(BuildStatus.SUCCESS)
                    .build();

            WireMock.setupServer(post(BUILD_QUEUE.getUrl()), HttpStatus.SC_OK, fakeBuild);
        }
        @Test(description = "User should be able to start build (with WireMock)", groups = {"Regression"})
        public void userStartsBuildWithWireMockTest() {
            var checkedBuildQueueRequest = new CheckedBase<Build>(Specifications.mockSpec(), BUILD_QUEUE);

            var build = checkedBuildQueueRequest.create(Build.builder()
                    .buildType(testData.getBuildType())
                    .build());

            softy.assertEquals(build.getState(), BuildState.FINISHED);
            softy.assertEquals(build.getStatus(), BuildStatus.SUCCESS);
        }


        @AfterMethod(alwaysRun = true)
        public void stopWireMockServer() {
            WireMock.stopServer();
        }
    }

