package com.samydevup.myspringbatchproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//@Service
public class SecondJobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SecondJobScheduler.class);

    @Autowired
    JobLauncher jobLauncher;//util pour le lancement des jobs du projet avec ous sans les jobParameters

    @Autowired
    @Qualifier("secondJob")
    Job secondJob;

    //le job s'executera chaque une minute
    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void secondJobStarter() {

        //mécanisme d'unicité des jobParameters, en y ajoutant la liste de paramètres jobRequestList
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);
        try {
            JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
            //util pour tracer des infos comme l'id du job en cours ...(id retrouvable dans la table batch_job_execution
            logger.info("Scheduled Job Execution id {} ", jobExecution.getId());
        } catch (Exception e) {
            logger.info("Exception while starting job");
        }
    }
}
