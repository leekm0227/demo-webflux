package com.example.voto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class VotoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VotoApplication.class, args);
    }
}
