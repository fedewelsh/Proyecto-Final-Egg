package com.egg.servix3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Servix3Application {

	public static void main(String[] args) {
		SpringApplication.run(Servix3Application.class, args);
	}

}
