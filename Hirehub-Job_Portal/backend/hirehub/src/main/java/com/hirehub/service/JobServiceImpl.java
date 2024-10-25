package com.hirehub.service;

import com.hirehub.dto.ApplicantDTO;
import com.hirehub.dto.Application;
import com.hirehub.dto.ApplicationStatus;
import com.hirehub.dto.JobDTO;
import com.hirehub.entity.Applicant;
import com.hirehub.entity.Job;
import com.hirehub.exception.JobPortalException;
import com.hirehub.repository.JobRepository;
import com.hirehub.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("jobService")
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobDTO postJob(JobDTO jobDTO) throws Exception {
        jobDTO.setId(Utilities.getNextSequence("jobs"));
        jobDTO.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getAllJobs() throws Exception {
        return jobRepository.findAll().stream().map((x) -> x.toDTO()).toList();
    }

    @Override
    public JobDTO getJob(Long id) throws JobPortalException {
        return jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND")).toDTO();
    }

    @Override
    public void applyJob(Long id, ApplicantDTO applicantDTO) throws Exception {
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
        List<Applicant> applicants = job.getApplicants();
        if(applicants == null){
            applicants = new ArrayList<>();
        }
        if(applicants.stream().filter((x)-> x.getApplicantId() == applicantDTO.getApplicantId()).toList().size()>0){throw new JobPortalException("JOB_APPLIED_ALREADY");}
        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDTO.toEntity());
        job.setApplicants(applicants);
        jobRepository.save(job);
    }

    @Override
    public List<JobDTO> getJobsPostedBy(Long id) throws Exception {
        return jobRepository.findByPostedBy(id).stream().map((x) -> x.toDTO()).toList();
    }

    @Override
    public void changeApplicationStatus(Application application) throws Exception {
        Job job = jobRepository.findById(application.getId()).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
        List<Applicant> applicants = job.getApplicants().stream().map((x)->{
            if(application.getApplicantId() == x.getApplicantId()){
                x.setApplicationStatus(application.getApplicationStatus());
                if(application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)){
                    x.setInterviewTime(application.getInterviewTime());
                }
            }
            return x;
        }).toList();

        job.setApplicants(applicants);
        jobRepository.save(job);
    }
}
