package com.test.tic.brick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TicBrickApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicBrickApplication.class, args);
	}

}
