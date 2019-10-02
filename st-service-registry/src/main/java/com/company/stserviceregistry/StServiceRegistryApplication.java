package com.company.stserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StServiceRegistryApplication.class, args);
	}

}
