package com.example.tutorial2;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelOpenTelemetry
@SpringBootApplication
public class Tutorial2Application {

    public static void main(String[] args) {
        SpringApplication.run(Tutorial2Application.class, args);
    }

}
