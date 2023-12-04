package com.egg.servix2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Servix2Application {

	public static void main(String[] args) {
		SpringApplication.run(Servix2Application.class, args);
	}

}
