package com.samydevup.myspringbatchproject.config;

import com.samydevup.myspringbatchproject.processor.FirstItemProcessor;
import com.samydevup.myspringbatchproject.reader.FirstItemReader;
import com.samydevup.myspringbatchproject.writer.FirstItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob {

    private static Logger logger = LoggerFactory.getLogger(SampleJob.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Bean
    public Job chunkJob() {
        logger.info("✨✨✨ démarrage du job secondJob() de JobWithChunckedOrientedSteps ");
        return jobBuilderFactory
                .get("Chunk Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }


    @Bean
    public Step firstChunkStep() {
        logger.info("👉 step firstChunkStep de JobWithChunckedOrientedSteps en cours ... ");
        return stepBuilderFactory.get("First Chunck Step")
                .<Integer, Long>chunk(4)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }


}
