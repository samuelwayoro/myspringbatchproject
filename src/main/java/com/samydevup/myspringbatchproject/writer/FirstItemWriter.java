package com.samydevup.myspringbatchproject.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<Long> {

    private static Logger logger = LoggerFactory.getLogger(FirstItemWriter.class);

    @Override
    public void write(List<? extends Long> items) throws Exception {
        logger.info("✍ Writer FirstItemWriter() en cours ...");
        for (Long item : items) {
            logger.info("{}", item);
        }
    }
}
