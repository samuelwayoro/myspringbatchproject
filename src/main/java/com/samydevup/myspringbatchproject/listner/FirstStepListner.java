package com.samydevup.myspringbatchproject.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListner implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(FirstStepListner.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("⏺⏺⏺lancement du step  {}  en cours ⏺⏺⏺", stepExecution.getStepName());
        logger.info(" context de son job {} ", stepExecution.getJobExecution().getExecutionContext());
        logger.info("context de ce step {} ", stepExecution.getExecutionContext());

        //rajout d'une donnée clé-valeur dans le context du step
        logger.info(" rajout de la variable {'SEC_KEY','SEC_VALUE'} dans le context de ce step ");
        stepExecution.getExecutionContext().put("SEC_KEY", "SEC_VALUE");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("fin du step avec le context suivant de son job {} ", stepExecution.getJobExecution().getExecutionContext());
        logger.info("fin du déroulement du step avec le context {} ", stepExecution.getExecutionContext());
        logger.info("🔚🔚🔚 fin du step {}", stepExecution.getStepName());
        return null;
    }
}
