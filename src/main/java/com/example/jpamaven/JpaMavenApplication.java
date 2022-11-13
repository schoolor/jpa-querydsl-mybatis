package com.example.jpamaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMavenApplication.class, args);
    }

}
