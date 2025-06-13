package com.samydevup.myspringbatchproject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.samydevup.myspringbatchproject.config")
public class MyspringbatchprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspringbatchprojectApplication.class, args);
    }

}
