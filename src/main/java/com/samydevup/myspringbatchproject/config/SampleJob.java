package com.samydevup.myspringbatchproject.config;

import com.samydevup.myspringbatchproject.model.StudentCsv;
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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

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
                .<StudentCsv, StudentCsv>chunk(3)
                .reader(flatFileItemReader())
                //.processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }


    public FlatFileItemReader<StudentCsv> flatFileItemReader() {
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();

        //configuration de La ressource
        flatFileItemReader.setResource(new FileSystemResource(
                new File("D:\\IntelliJJavaProject\\myspringbatchproject\\inputFiles\\students.csv")
        ));

        /**
         * Configuration du LineMapper composé de :
         *  - Line Tokenizer comprenant : un délimiteur (par défaut qui est une ",") et la mention des colonnes de l'entête du fichier
         *  - Le Bean Mapper : qui est la configuration d'une ligne d'item lû dans le fichier.
         */

        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                        setDelimiter("|");
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>() {
                    {
                        setTargetType(StudentCsv.class);
                    }
                });
            }
        });
        //le nombre de lignes à sauter à partir du haut pendant la lecture
        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }

}
