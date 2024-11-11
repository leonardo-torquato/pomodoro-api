package com.example.pomodoroApp.pomodoro_api;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class PomodoroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PomodoroApiApplication.class, args);
	}

}
