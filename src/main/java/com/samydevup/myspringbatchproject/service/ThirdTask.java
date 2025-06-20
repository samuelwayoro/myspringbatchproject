package com.samydevup.myspringbatchproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class ThirdTask implements Tasklet {
    private static Logger logger = LoggerFactory.getLogger(ThirdTask.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        logger.info("🔜 thirdTask de jobWithTaskletsSteps en cours ...  ");
        return RepeatStatus.FINISHED;
    }
}
