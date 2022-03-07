package com.dgtt.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
@EntityScan("com.dgtt.core.entity")
@EnableJpaRepositories(basePackages = {"com.dgtt.core.repository", "com.dgtt.core.repository.impl"})
public class DgttBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgttBusinessApplication.class, args);
    }

}
