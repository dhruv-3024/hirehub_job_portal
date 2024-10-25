package com.hirehub.api;

import com.hirehub.dto.*;
import com.hirehub.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobAPI {
    @Autowired
    private JobService jobService;

    @PostMapping("/postJob")
    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDTO) throws Exception { //for JSON obj, web req body
        jobDTO = jobService.postJob(jobDTO);
        return new ResponseEntity<>(jobDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobDTO>> getAllJobs() throws Exception { //for JSON obj, web req body

        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    @GetMapping("/getJob/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) throws Exception {

        return new ResponseEntity<>(jobService.getJob(id), HttpStatus.OK);
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<ResponseDTO> applyJob(@PathVariable Long id, @RequestBody  ApplicantDTO applicantDTO ) throws Exception { //for JSON obj, web req body
        jobService.applyJob(id,applicantDTO);
        return new ResponseEntity<>(new ResponseDTO("Applied Successfully"), HttpStatus.OK);
    }

    @GetMapping("/postedBy/{id}")
    public ResponseEntity<List<JobDTO>> getJobsPostedBy(@PathVariable Long id) throws Exception { //for JSON obj, web req body

        return new ResponseEntity<>(jobService.getJobsPostedBy(id), HttpStatus.OK);
    }

    @PostMapping("/changeApplicationStatus")
    public ResponseEntity<ResponseDTO> changeApplicationStatus(@RequestBody Application application) throws Exception { //for JSON obj, web req body
        jobService.changeApplicationStatus(application);
        return new ResponseEntity<>(new ResponseDTO("Application Status changed successfully."), HttpStatus.OK);
    }

}
