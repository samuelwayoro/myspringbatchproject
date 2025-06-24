package com.samydevup.myspringbatchproject.writer;

import com.samydevup.myspringbatchproject.model.StudentCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentCsv> {

    private static Logger logger = LoggerFactory.getLogger(FirstItemWriter.class);

    @Override
    public void write(List<? extends StudentCsv> items) throws Exception {
        logger.info("↪  Wrtier en cours ....");
        items.forEach(System.out::println);
    }
}
