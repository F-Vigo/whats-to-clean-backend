package com.example.whatstocleanbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.example.whatstocleanbackend.repository")
@EntityScan("com.example.whatstocleanbackend.domain")
@EnableScheduling
@EnableAspectJAutoProxy
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
