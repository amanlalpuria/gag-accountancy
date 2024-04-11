package com.lalpuria.gag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GagApplication {
	public static void main(String[] args) {
		SpringApplication.run(GagApplication.class, args);
	}
}
