package com.samydevup.myspringbatchproject.config;

import com.samydevup.myspringbatchproject.model.StudentCsv;
import com.samydevup.myspringbatchproject.model.StudentJson;
import com.samydevup.myspringbatchproject.model.StudentXml;
import com.samydevup.myspringbatchproject.processor.FirstItemProcessor;
import com.samydevup.myspringbatchproject.reader.FirstItemReader;
import com.samydevup.myspringbatchproject.writer.FirstItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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
                .get("new chunk Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }


    @Bean
    public Step firstChunkStep() {
        logger.info("👉 step firstChunkStep de JobWithChunckedOrientedSteps en cours ... ");
        return stepBuilderFactory.get("First Chunck Step")
                .<StudentXml, StudentXml>chunk(3)
                //.reader(flatFileItemReader(null))//null à cause de la valeur paramétrée du fichier avec @Value
                //.reader(jsonJsonItemReader(null))
                .reader(staxEventItemReader(null))
                //.processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }


    /**
     * ATTENTION : la valeur de inputFile est paramétré
     * dans le "run/Debug Configuration dans l'EDI"
     *
     * @param filename
     * @return
     */

    @Bean
    @StepScope
    public FlatFileItemReader<StudentCsv> flatFileItemReader(
            @Value("#{jobParameters['inputFile']}") String filename) {
        return new FlatFileItemReaderBuilder<StudentCsv>()
                .name("flatFileItemReader")
                .resource(new FileSystemResource(filename)) // ou ClassPathResource
                .delimited()
                .names("ID", "First Name", "Last Name", "Email") // doit correspondre aux noms des attributs
                .targetType(StudentCsv.class)
                .linesToSkip(1) // ignore l’en-tête
                .build();
    }

    /**
     * reader for json input file
     *
     * @param filename
     * @return
     */
    @Bean
    @StepScope
    public JsonItemReader<StudentJson> jsonJsonItemReader(
            @Value("#{jobParameters['inputFile']}") String filename) {

        return new JsonItemReaderBuilder<StudentJson>()
                .name("studentJsonItemReader")
                .resource(new FileSystemResource(filename))
                .jsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class))
                .build();
    }

    /**
     * reader for xml input file
     *
     * @param fileSystemResource
     * @return
     */
    @Bean
    @StepScope
    public StaxEventItemReader<StudentXml> staxEventItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {

        logger.info("le fichier inputFile " + fileSystemResource);
        //instancie un objet de type StaxEventItemReader
        StaxEventItemReader<StudentXml> staxEventItemReader = new StaxEventItemReader<>();

        //setting du fichier source xml
        staxEventItemReader.setResource(fileSystemResource);
        //setting du fragment à utiliser dans ce fichier source xml
        staxEventItemReader.setFragmentRootElementName("student");

        //instanciation d'un objet de type Jaxb2Marshaller pour une opération de "unMarchall" (mapping d'un xml vers un objet bean)
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(StudentXml.class);//classe à utiliser pour l'opération de mappage

        staxEventItemReader.setUnmarshaller(marshaller);
        return staxEventItemReader;
    }


}
