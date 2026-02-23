package com.example.teamcity.requests;

import com.example.teamcity.enums.Endpoint;
import com.example.teamcity.models.User;
import com.example.teamcity.requests.unchecked.UncheckedBase;
import com.example.teamcity.spec.Specifications;
import io.restassured.specification.RequestSpecification;

import java.util.EnumMap;

public class UncheckedRequests {
    private final EnumMap<Endpoint, UncheckedBase> requests = new EnumMap<>(Endpoint.class);

    public UncheckedRequests(RequestSpecification spec) {
        for (var endpoint: Endpoint.values()) {
            requests.put(endpoint, new UncheckedBase(spec, endpoint));
        }
    }

    public static UncheckedRequests userRequest (User user){
        return new UncheckedRequests(Specifications.authSpec(user));
    }

    public UncheckedBase getRequest(Endpoint endpoint) {
        return requests.get(endpoint);
    }
}