package com.czetsuyatech.jobs.web.controller;

import com.czetsuyatech.jobs.api.dtos.outbound.JobWithApplicantsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JobController {

  private final ApplicantProxy applicantProxy;

  @GetMapping("/job-with-applicant-profiles")
  public ResponseEntity listJobsWithApplicantProfiles() {

    log.debug("get job details with applicants");

    JobWithApplicantsDto result = new JobWithApplicantsDto();
    result.setJob("Java Developer");

    result.setApplicants(applicantProxy.getApplicantsByJob());

    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/job-with-top-applicants")
  public ResponseEntity listJobsWithTopApplicants() {

    log.debug("get job details with applicants");

    JobWithApplicantsDto result = new JobWithApplicantsDto();
    result.setJob("AWS Solutions Architect");

    result.setApplicants(applicantProxy.getTopApplicantsByJob());

    return ResponseEntity.ok().body(result);
  }
}