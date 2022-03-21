package com.czetsuyatech.jobs.web.controllers;

import com.czetsuyatech.jobs.api.dtos.outbound.JobWithApplicants;
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

  @GetMapping("/")
  public ResponseEntity listJobsWithApplicantProfiles() {

    log.debug("get job details with applicants");

    JobWithApplicants result = new JobWithApplicants();
    result.setJob("Java Developer");

    result.setApplicants(applicantProxy.getApplicantsByJob());

    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/top")
  public ResponseEntity listJobsWithTopApplicants() {

    log.debug("get job details with applicants");

    JobWithApplicants result = new JobWithApplicants();
    result.setJob("AWS Solutions Architect");

    result.setApplicants(applicantProxy.getTopApplicantsByJob());

    return ResponseEntity.ok().body(result);
  }
}