package com.uc.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
	@PropertySource("classpath:application.properties"),
	@PropertySource("classpath:application-common.properties")
})
@SpringBootApplication(scanBasePackages = {"com.uc.common", "com.uc.services"})
public class UcServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcServicesApplication.class, args);
	}

}
