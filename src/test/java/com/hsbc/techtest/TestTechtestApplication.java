package com.hsbc.techtest;

import org.springframework.boot.SpringApplication;

public class TestTechtestApplication {

	public static void main(String[] args) {
		SpringApplication.from(TechtestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
