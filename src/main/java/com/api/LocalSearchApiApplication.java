package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LocalSearchApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalSearchApiApplication.class, args);
	}

}
