package com.cristian.TaskApplication;

import com.cristian.TaskApplication.presentation.SystemTaskFX;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) {
		//SpringApplication.run(TaskApplication.class, args);
		Application.launch(SystemTaskFX.class, args);
	}

}
