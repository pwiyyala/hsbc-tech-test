package com.hsbc.techtest;

import com.hsbc.techtest.config.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * The type Test techtest application.
 */
public class TestTechtestApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
public static void main(String[] args) {
		SpringApplication.from(TechtestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
