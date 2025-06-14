package com.samydevup.myspringbatchproject.listner;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListner implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("🔄 Le job : " + jobExecution.getJobInstance().getJobName() + "  commence ...");
        System.out.println("ses paramètres sont  : " + jobExecution.getJobParameters());
        System.out.println("son context est le suivant  : " + jobExecution.getExecutionContext());

        System.out.println("rajoutons les parametres suivant nom=wayoro dans le context du job , afin de l'utiliser dans le step : secondStep");

        jobExecution.getExecutionContext().put("Nom", "WAYORO");//ses paramètres seon disponible dans le code et visible dans le afterJob()
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("✅ Le job : " + jobExecution.getJobInstance().getJobName() + " est terminé avec les paramètres suivant : " + jobExecution.getJobParameters() + "  et le context suivant  " + jobExecution.getExecutionContext());
    }
}
