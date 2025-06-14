package com.hsbc.techtest.config;

import com.hsbc.techtest.exceptions.errorhandlers.WeatherMapClientErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class WeatherMapClientConfiguration {

    @Value("${hsbc.techtest.feign.retry.period:100}")
    private int period;

    @Value("${hsbc.techtest.feign.retry.max.period:1000}")
    private int maxPeriod;

    @Value("${hsbc.techtest.feign.retry.max.attempts:5}")
    private int maxAttempts;

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(period, maxPeriod, maxAttempts);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
    return new WeatherMapClientErrorDecoder();
    }

}
