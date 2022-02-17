package com.udemy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.udemy")
@EntityScan("com.udemy.domain")
public class ApplicationStart implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
