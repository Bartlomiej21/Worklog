package io.calamari.recruit.worklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class WorklogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorklogApplication.class, args);
	}

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}
}
