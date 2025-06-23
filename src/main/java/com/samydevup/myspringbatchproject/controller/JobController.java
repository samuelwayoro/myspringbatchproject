package com.samydevup.myspringbatchproject.controller;

import com.samydevup.myspringbatchproject.request.JobParamsRequest;
import com.samydevup.myspringbatchproject.service.JobService;
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

    @Autowired
    JobService jobService;

    @GetMapping("/start/{jobName}")
    public String startJobs(@PathVariable String jobName, @RequestBody List<JobParamsRequest> jobParamRequestList) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobService.startJob(jobName,jobParamRequestList);
        return "job started ...";
    }

}
