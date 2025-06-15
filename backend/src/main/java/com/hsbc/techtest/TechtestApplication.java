package com.hsbc.techtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The type Techtest application.
 */
@SpringBootApplication
@EnableFeignClients
public class TechtestApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
public static void main(String[] args) {
		SpringApplication.run(TechtestApplication.class, args);
	}

}
