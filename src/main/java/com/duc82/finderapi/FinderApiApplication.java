package com.duc82.finderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication()
@EnableJpaAuditing
public class FinderApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinderApiApplication.class, args);
    }
}
