package com.hsbc.techtest.exceptions.errorhandlers;

import com.google.common.io.CharStreams;
import com.hsbc.techtest.exceptions.InvalidRequestException;
import com.hsbc.techtest.exceptions.ResourceFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class WeatherMapClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        String body = "";
        Response.Body responseBody = response.body();

        body =
                Optional.ofNullable(responseBody)
                        .map(
                                resBody -> {
                                    try (Reader reader = resBody.asReader(StandardCharsets.UTF_8)) {
                                        return CharStreams.toString(reader);
                                    } catch (IOException e) {
                                        log.warn("Unable to parse the response from status", e);
                                        return "";
                                    }
                                })
                        .orElse("");

        return switch (response.status()) {
            case 400 -> new InvalidRequestException(body);
            case 404 -> new ResourceFoundException();
            case 500, 502, 503, 504 ->
                    new RetryableException(
                            response.status(),
                            exception.getMessage(),
                            response.request().httpMethod(),
                            exception,
                            50L, // The retry interval
                            response.request());
            default -> exception;
        };
    }
}
