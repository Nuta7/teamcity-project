package com.example.teamcity.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.example.teamcity.models.BaseModel;
import lombok.SneakyThrows;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.github.tomakehurst.wiremock.common.ContentTypes.CONTENT_TYPE;

public final class WireMock {

    private static WireMockServer wireMockServer;

    private WireMock() {
    }

    @SneakyThrows
    public static void setupServer() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(8081);
            wireMockServer.start();
        }
    }
    public static void setupStub(MappingBuilder mappingBuilder, int status, BaseModel model) {
        try {

            var jsonModel = new ObjectMapper().writeValueAsString(model);

            wireMockServer.stubFor(mappingBuilder
                    .willReturn(aResponse()
                            .withStatus(status)
                            .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                            .withBody(jsonModel)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка конвертации модели в JSON", e);
        }
    }

    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }

}
