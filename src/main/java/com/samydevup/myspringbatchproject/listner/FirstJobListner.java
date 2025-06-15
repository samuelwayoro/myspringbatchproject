package com.samydevup.myspringbatchproject.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListner implements JobExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(FirstJobListner.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info(" 🔄 🔄 🔄 Le job : {}  commence ... 🔄 🔄 🔄  ", jobExecution.getJobInstance().getJobName());
        logger.info(" ses paramètres sont {}", jobExecution.getJobParameters());
        logger.info(" son context est le suivant : {} ", jobExecution.getExecutionContext());

        logger.info("rajout de paramètres suivant {'nom','WAYORO'} dans le context du job , pour utilisation dans le step 'secondStep' ");
        jobExecution.getExecutionContext().put("Nom", "WAYORO");//ses paramètres seon disponible dans le code et visible dans le afterJob()
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info(" ✅✅✅ Le job : {} est terminé avec les paramètres suivant {} ", jobExecution.getJobInstance().getJobName(), jobExecution.getJobParameters());
    }
}
