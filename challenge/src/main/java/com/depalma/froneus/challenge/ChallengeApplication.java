package com.depalma.froneus.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@ComponentScan(basePackages = {"com.depalma.froneus.challenge"},
		excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.example.app.exclude.*"))

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
