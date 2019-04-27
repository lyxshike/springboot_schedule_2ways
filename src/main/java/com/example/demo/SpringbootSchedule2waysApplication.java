package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
@EnableScheduling
@SpringBootApplication
public class SpringbootSchedule2waysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSchedule2waysApplication.class, args);
	}

	

}
