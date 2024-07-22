package com.example.mat_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication
public class MatBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatBackendApplication.class, args);
	}

}
