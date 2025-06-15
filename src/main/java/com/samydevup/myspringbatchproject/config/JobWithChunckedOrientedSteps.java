package com.samydevup.myspringbatchproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobWithChunckedOrientedSteps {

    private static Logger logger = LoggerFactory.getLogger(JobWithChunckedOrientedSteps.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    private Job secondJob() {
        logger.info("✨✨✨ demarrage de {} ", secondJob().getName());
        return jobBuilderFactory.get("Second Job").incrementer(new RunIdIncrementer()).build();
    }

}
