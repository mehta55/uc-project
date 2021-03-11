package com.uc.offers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
	@PropertySource("classpath:application.properties"),
	@PropertySource("classpath:application-common.properties")
})
@SpringBootApplication(scanBasePackages = {"com.uc.common", "com.uc.offers"})
public class UcOffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcOffersApplication.class, args);
	}

}
