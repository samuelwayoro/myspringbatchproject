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
public class JobWithChunckedOrientedSteps {

    private static Logger logger = LoggerFactory.getLogger(JobWithChunckedOrientedSteps.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /**
     * Injection des reader,processor et writer
     *
     * @return
     */

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Autowired
    private FirstItemProcessor firstItemProcessor;


    @Bean
    private Job secondJob() {
        logger.info("✨✨✨ demarrage du job :  {} de type chunk-oriented step  ", secondJob().getName());
        return jobBuilderFactory
                .get("Second Job")
                .start(firstChunkStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }


    /**
     * Premier step orienté chunk, contenant un reader,processor et writer .
     * Il est ensuite raccordé au Job ci-dessus .
     * @return
     */

    @Bean
    private Step firstChunkStep() {
        logger.info("👉 step firstChunkStep en cours ... ");
        return stepBuilderFactory.get("First Chunck Step")
                .<Integer, Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

}
