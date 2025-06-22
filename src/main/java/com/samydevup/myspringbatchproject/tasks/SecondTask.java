package com.samydevup.myspringbatchproject.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondTask implements Tasklet {
    private static Logger logger = LoggerFactory.getLogger(SecondTask.class);

    /***
     *
     * @param stepContribution
     * @param chunkContext : permet d'accéder aux paramètres du context
     * @return
     * @throws Exception
     */

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        logger.info("🔜 secondTask en cours ...  ");
        logger.info("⚠⚠⚠ context parameters are {} ⚠⚠⚠ ", chunkContext.getStepContext().getJobExecutionContext());
        return RepeatStatus.FINISHED;
    }
}
