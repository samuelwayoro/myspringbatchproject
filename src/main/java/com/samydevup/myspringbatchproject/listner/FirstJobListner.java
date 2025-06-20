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
        logger.info("⏺ DEBUT DU FIRSTJOBLISTNER :  méthode beforeJob du listner FirstJobListner");
         logger.info(" rajout de paramètres suivant {'nom','WAYORO'} dans le context de firstJob via FirstJobListner , pour utilisation dans le step 'secondStep' ");
        jobExecution.getExecutionContext().put("Nom", "WAYORO");//ses paramètres seon disponible dans le code et visible dans le afterJob()
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("⏺ FIN DU FIRSTJOBLISTNER :  méthode beforeJob du listner FirstJobListner");

    }
}
