package com.licenta;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Turism GPT API",
                version = "1.0",
                description = "Documentația RESTful pentru toate entitățile"
        )
)

@SpringBootApplication
public class Licenta2Application {
    public static void main(String[] args) {
        SpringApplication.run(Licenta2Application.class, args);
    }
}
