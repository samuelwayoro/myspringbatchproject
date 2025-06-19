package com.samydevup.myspringbatchproject.config;

import com.samydevup.myspringbatchproject.processor.FirstItemProcessor;
import com.samydevup.myspringbatchproject.reader.FirstItemReader;
import com.samydevup.myspringbatchproject.service.SecondTask;
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
    private JobBuilderFactory jobBuilderFactory;

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


    /***
     * INJECTION DES DEUX TASKLET
     * IMPLEMENTEES DEPUIS LE PACKAGE SERVICE
     */
    @Autowired
    private SecondTask secondTasklet;


    @Bean
    public Job secondJob() {
        logger.info("✨✨✨ démarrage du job secondJob() de type chunk-oriented step  ");
        return jobBuilderFactory
                .get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(simpleTaskletStep())
                .build();
    }


    /**
     * Premier step orienté chunk, contenant un reader,processor et writer
     * Il est ensuite raccordé au Job ci-dessus
     *
     * @return
     */

    @Bean
    public Step firstChunkStep() {
        logger.info("👉 step firstChunkStep en cours ... ");
        return stepBuilderFactory.get("First Chunck Step")
                .<Integer, Long>chunk(4)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }


    private Step simpleTaskletStep() {
        logger.info("JobWithChunckedOrientedSteps simpleTaskletStep()...");
        return stepBuilderFactory.get("simple Tasklet Step")
                .tasklet(secondTasklet)
                .build();
    }


}
