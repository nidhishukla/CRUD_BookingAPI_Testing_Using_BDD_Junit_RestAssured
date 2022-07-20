package com.foundations.common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractBaseService {

    public final String baseUrl;

    protected AbstractBaseService(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected ExtractableResponse<Response> post(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .post(path)
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> put(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .put(path)
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> delete(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .delete(path)
                .then()
                .extract();
    }

    protected RequestSpecification prepareUserRequest(String baseUrl, Map<String, String> headers, String contentType, Object body) {
        return prepareUserRequestWithBody(baseUrl, headers, contentType, body);
    }

    protected RequestSpecification prepareUserRequestWithBody(String baseUrl, Map<String, String> headers,
            String contentType, Object body) {
        var spec = initRequestSpecification(baseUrl, headers, contentType);
        if (body != null) {
            spec = spec.body(body);
        }
        return spec;
    }

    private RequestSpecification initRequestSpecification(String baseUrl, Map<String, String> headers,
            String contentType) {
        return RestAssured
                .given(new RequestSpecBuilder().setBaseUri(baseUrl).build())
                .filters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .request()
                .headers(Optional.ofNullable(headers).orElse(Collections.emptyMap()))
                .contentType(Optional.ofNullable(contentType).orElse(ContentType.JSON.toString()))
                .when();
    }

    // Creates http header for calls with Cookie
    protected Map<String, String> createHttpHeaders(String token) {
        Map<String, String> headers = new HashMap<>();

        headers.put("Cookie", "token="+token);

        return headers;
    }

}
