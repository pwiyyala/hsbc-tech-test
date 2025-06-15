package com.hsbc.techtest.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * The type Weather map client configuration.
 */
public class WeatherMapClientConfiguration {

  @Value("${hsbc.techtest.feign.retry.period:100}")
  private int period;

  @Value("${hsbc.techtest.feign.retry.max.period:1000}")
  private int maxPeriod;

  @Value("${hsbc.techtest.feign.retry.max.attempts:5}")
  private int maxAttempts;

  /**
   * Feign logger level logger . level.
   *
   * @return the logger . level
   */
@Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }

  /**
   * Retryer retryer.
   *
   * @return the retryer
   */
@Bean
  public Retryer retryer() {
    return new Retryer.Default(period, maxPeriod, maxAttempts);
  }

  /**
   * Error decoder error decoder.
   *
   * @return the error decoder
   */
@Bean
  public ErrorDecoder errorDecoder() {
    return new WeatherMapClientErrorDecoder();
  }
}
