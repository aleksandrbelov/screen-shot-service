package com.detectify.screenshotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ScreenShotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenShotServiceApplication.class, args);
	}

}
