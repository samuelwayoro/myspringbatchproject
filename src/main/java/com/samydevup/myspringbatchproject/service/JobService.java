package com.samydevup.myspringbatchproject.service;

import com.samydevup.myspringbatchproject.request.JobParamsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    private static Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    JobLauncher jobLauncher;//util pour le lancement des jobs du projet avec ous sans les jobParameters

    @Autowired
    @Qualifier("firstJob")
    Job firstJob;

    @Autowired
    @Qualifier("secondJob")
    Job secondJob;

    @Async
    public void startJob(String jobName, List<JobParamsRequest> jobParamRequestList) {
        //mécanisme d'unicité des jobParameters, en y ajoutant la liste de paramètres jobRequestList
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));
        jobParamRequestList.forEach(jobParamsRequest -> params.put(jobParamsRequest.getParamKey(), new JobParameter(jobParamsRequest.getParamValue())));
        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = null;
            if (jobName.equals("First Job")) {
                jobExecution = jobLauncher.run(firstJob, jobParameters);
            } else if (jobName.equals("Second Job")) {
                jobExecution = jobLauncher.run(secondJob, jobParameters);
            }
            //util pour tracer des infos comme l'id du job en cours ...(id retrouvable dans la table batch_job_execution
            logger.info("Job Execution id {} ", jobExecution.getId());
        } catch (Exception e) {
            logger.info("Exception while starting job");
        }
    }


}
