package com.samydevup.myspringbatchproject.controller;

import com.samydevup.myspringbatchproject.request.JobParamsRequest;
import com.samydevup.myspringbatchproject.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    JobService jobService;

    /**
     * Endpoint for start a job from its name
     *
     * @param jobName
     * @param jobParamRequestList
     * @return
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobParametersInvalidException
     * @throws JobRestartException
     */
    @GetMapping("/start/{jobName}")
    public String startJobs(@PathVariable String jobName, @RequestBody List<JobParamsRequest> jobParamRequestList) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobService.startJob(jobName, jobParamRequestList);
        return "job started ...";
    }


    @DeleteMapping("/stop/{jobExecutionId}")
    public String stopJob(@PathVariable Long jobExecutionId) {
        logger.info("inside stopJob endpoint ");
        jobService.stopJob(jobExecutionId);
        return "Job stopped...";
    }


}
