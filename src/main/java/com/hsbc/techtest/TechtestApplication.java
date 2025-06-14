package com.hsbc.techtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechtestApplication.class, args);
	}

}
