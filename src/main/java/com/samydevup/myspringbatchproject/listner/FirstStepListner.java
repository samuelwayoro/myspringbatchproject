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
        logger.info("⏺ DEBUT DU FIRSTSTEPLISTNER :  méthode beforeStep du listner FIRSTSTEPLISTNER");
        //rajout d'une donnée clé-valeur dans le context du step
        logger.info("rajout de la variable {'SEC_KEY','SEC_VALUE'} dans le context de ce step ");
        stepExecution.getExecutionContext().put("SEC_KEY", "SEC_VALUE");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("⏺ méthode afterStep du listner FirstStepListner");
        logger.info("⏺ FIN DU FIRSTSTEPLISTNER :  méthode afteStep du listner FIRSTSTEPLISTNER");

        return null;
    }
}
