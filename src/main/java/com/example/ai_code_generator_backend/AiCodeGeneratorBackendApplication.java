package com.example.ai_code_generator_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiCodeGeneratorBackendApplication {



	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_URL", dotenv.get("DB_URL"));


		SpringApplication.run(AiCodeGeneratorBackendApplication.class, args);
	}

}
