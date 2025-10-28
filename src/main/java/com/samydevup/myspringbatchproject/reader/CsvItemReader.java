package com.samydevup.myspringbatchproject.reader;

import com.samydevup.myspringbatchproject.model.StudentCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CsvItemReader {

    private static final Logger LOG = LoggerFactory.getLogger(CsvItemReader.class);


    //@Bean
    public FlatFileItemReader<StudentCsv> studentCsvFlatFileItemReader(String inputFile) {

        LOG.info("🚀 FirstItemProcessor en cours avec {}", inputFile);

        return new FlatFileItemReaderBuilder<StudentCsv>()
                .name("flatFileItemReader")
                .resource(new FileSystemResource(inputFile))
                .delimited()
                .names("ID", "First Name", "Last Name", "Email") // doit correspondre aux noms des attributs
                .targetType(StudentCsv.class)
                .linesToSkip(1) // ignore l’en-tête
                .build();
    }

}
