package com.samydevup.myspringbatchproject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.samydevup.myspringbatchproject.config", "com.samydevup.myspringbatchproject.service", "com.samydevup.myspringbatchproject.listner","com.samydevup.myspringbatchproject.reader","com.samydevup.myspringbatchproject.processor","com.samydevup.myspringbatchproject.writer"})
public class MyspringbatchprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspringbatchprojectApplication.class, args);
    }

}
