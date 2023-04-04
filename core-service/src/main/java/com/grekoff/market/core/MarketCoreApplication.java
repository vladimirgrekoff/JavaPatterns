package com.grekoff.market.core;

import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarketCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketCoreApplication.class, args);
	}


//	@Bean
//	SpringDocConfiguration springDocConfiguration() {
//		return new SpringDocConfiguration();
//	}
//
//	@Bean
//	SpringDocConfigProperties springDocConfigProperties() {
//		return new SpringDocConfigProperties();
//	}
//
//	@Bean
//	ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties) {
//		return new ObjectMapperProvider(springDocConfigProperties);
//	}
}
