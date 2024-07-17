package com.mladin.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class ForumApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	public static String getDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusNanos(localDateTime.getNano());

		return localDateTime.toString().replace("T", " | ");
	}
}
