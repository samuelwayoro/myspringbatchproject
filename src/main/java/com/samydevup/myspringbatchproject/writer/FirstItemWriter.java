package com.samydevup.myspringbatchproject.writer;

import com.samydevup.myspringbatchproject.model.StudentDTO;
import com.samydevup.myspringbatchproject.model.StudentXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentDTO> {

    private static Logger logger = LoggerFactory.getLogger(FirstItemWriter.class);

    @Override
    public void write(List<? extends StudentDTO> items) throws Exception {
        logger.info(" ↪  Writer en cours ....");
        items.forEach(System.out::println);
    }
}
