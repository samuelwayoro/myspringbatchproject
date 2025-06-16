package com.samydevup.myspringbatchproject.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<Integer, Long> {

    private static Logger logger = LoggerFactory.getLogger(FirstItemProcessor.class);

    @Override
    public Long process(Integer item) throws Exception {
        logger.info("🚀 FirstItemProcessor en cours   ");
        Long l = Long.valueOf(20 + item);
        return l;
    }
}
