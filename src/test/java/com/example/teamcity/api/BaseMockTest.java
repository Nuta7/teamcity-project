package com.example.teamcity.api;

import com.example.teamcity.common.WireMock;
import com.example.teamcity.enums.BuildState;
import com.example.teamcity.enums.BuildStatus;
import com.example.teamcity.generators.TestDataStorage;
import com.example.teamcity.models.Build;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.ThreadLocalRandom;

import static com.example.teamcity.enums.Endpoint.BUILD_QUEUE;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

public class BaseMockTest extends BaseApiTest{
    @BeforeMethod(alwaysRun = true)
    public void setupMockServer(){
    WireMock.setupServer();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
        TestDataStorage.getStorage().clear();
        WireMock.stopServer();
    }
    protected void setupBuildQueueStub(BuildState state, BuildStatus status) {
        setupBuildQueueStub(testData.getBuildType().getId(), state, status);
    }
    protected void setupBuildQueueStub(String buildTypeId, BuildState state, BuildStatus status) {
        var fakeId = ThreadLocalRandom.current().nextLong(1000, 1000000);
        var fakeBuild = Build.builder()
                .id(fakeId)
                .buildTypeId(buildTypeId)
                .state(state)
                .status(status)
                .build();
        WireMock.setupStub(post(BUILD_QUEUE.getUrl()), HttpStatus.SC_OK, fakeBuild);
        WireMock.setupStub(get(BUILD_QUEUE.getUrl()+ "/id%3A" + fakeId), HttpStatus.SC_OK, fakeBuild);
    }
}

