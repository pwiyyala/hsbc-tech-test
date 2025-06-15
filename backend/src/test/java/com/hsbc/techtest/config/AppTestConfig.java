package com.hsbc.techtest.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type App test config.
 */
@TestConfiguration
public class AppTestConfig {

    /**
     * Rest template test rest template.
     *
     * @return the test rest template
     */
@Bean
    TestRestTemplate restTemplate() {
        return new TestRestTemplate();
    }
}
