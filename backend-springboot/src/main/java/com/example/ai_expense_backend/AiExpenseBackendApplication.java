package com.example.ai_expense_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AiExpenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiExpenseBackendApplication.class, args);
	}

}
