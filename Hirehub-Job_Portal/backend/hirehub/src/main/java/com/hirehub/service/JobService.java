package com.hirehub.service;

import com.hirehub.dto.ApplicantDTO;
import com.hirehub.dto.Application;
import com.hirehub.dto.JobDTO;
import com.hirehub.exception.JobPortalException;

import java.util.List;

public interface JobService {

    JobDTO postJob(JobDTO jobDTO) throws Exception;

    List<JobDTO> getAllJobs() throws Exception;

    JobDTO getJob(Long id) throws JobPortalException;

    void applyJob(Long id, ApplicantDTO applicantDTO) throws Exception;

    List<JobDTO> getJobsPostedBy(Long id) throws Exception;

    void changeApplicationStatus(Application application) throws Exception;
}
