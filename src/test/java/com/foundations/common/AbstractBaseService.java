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
//BaseMethod for initiating the baseURL and enabling logger for Rest assured
    protected AbstractBaseService(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    //Common method for executing All POST Request
    protected ExtractableResponse<Response> get(String path) {
        return prepareUserRequest(baseUrl, null,null,null)
                .get(path)
                .then()
                .extract();
    }


//Common method for executing All POST Request
    protected ExtractableResponse<Response> post(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .post(path)
                .then()
                .extract();
    }
    //Common method for executing All PUT Request
    protected ExtractableResponse<Response> put(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .put(path)
                .then()
                .extract();
    }
    //Common method for executing All PUT Request
    protected ExtractableResponse<Response> patch(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .patch(path)
                .then()
                .extract();
    }
    //Common method for executing All DELETE Request
    protected ExtractableResponse<Response> delete(String path, Map<String, String> headers, Object body) {
        return prepareUserRequest(baseUrl, headers, ContentType.JSON.toString(),body)
                .delete(path)
                .then()
                .extract();
    }
    //Common method for creating request without body and Attaching it to Request Specification
    protected RequestSpecification prepareUserRequest(String baseUrl, Map<String, String> headers, String contentType, Object body) {
        return prepareUserRequestWithBody(baseUrl, headers, contentType, body);
    }

    //Common method for creating request with body and Attaching it to Request Specification
    protected RequestSpecification prepareUserRequestWithBody(String baseUrl, Map<String, String> headers,
            String contentType, Object body) {
        var spec = initRequestSpecification(baseUrl, headers, contentType);
        if (body != null) {
            spec = spec.body(body);
        }
        return spec;
    }

    //Common method for creating request without body and Attaching it to Request Specification
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
