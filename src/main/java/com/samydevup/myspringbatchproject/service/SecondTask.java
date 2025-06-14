package com.samydevup.myspringbatchproject.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondTask implements Tasklet {

    /***
     *
     * @param stepContribution
     * @param chunkContext : permet d'accéder aux paramètres du context
     * @return
     * @throws Exception
     */

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("--- THIS IS THE SECOND TASKLET STEP WITH USE FIRSTJOBCONTEXT PARAMETERS , INTRODUCED VIA FIRSTJOBLISTNER ---");
        System.out.println("💧💧💧 context parameters are : 💧💧💧 " + chunkContext.getStepContext().getJobExecutionContext());
        return RepeatStatus.FINISHED;
    }
}
