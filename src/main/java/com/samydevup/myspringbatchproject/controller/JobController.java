package com.samydevup.myspringbatchproject.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobLauncher jobLauncher;//util pour le lancement des jobs du projet avec ous sans les jobParameters


    @Autowired
    @Qualifier("firstJob")
    Job firstJob;


    @Autowired
    @Qualifier("secondJob")
    Job secondJob;


    @GetMapping("/start/{jobName}")
    public String startJobs(@PathVariable String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        //mécanisme d'unicité des jobParameters

        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);

        if(jobName.equals("First Job")){
            jobLauncher.run(firstJob, jobParameters);
        } else if (jobName.equals("Second Job")) {
            jobLauncher.run(secondJob, jobParameters);
        }
        return "job started ...";
    }

}
