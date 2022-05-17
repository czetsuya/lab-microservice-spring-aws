package com.czetsuyatech.jobs.web.controllers;

import com.czetsuyatech.jobs.api.dtos.outbound.JobWithApplicants;
import com.czetsuyatech.jobs.services.JobService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JobController {

  private final ApplicantProxy applicantProxy;
  private final JobService jobService;
  private final Executor executor;

  public static final long TIMEOUT = 300000L;

  @GetMapping("/profiles")
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

  @GetMapping("/thread-test")
  public DeferredResult<String> threadTest() {

    log.debug("thread test");

    DeferredResult<String> result = new DeferredResult<>(TIMEOUT);

    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> applicantProxy.threadTest(), executor);

    future.thenApply(result::setResult)
        .exceptionally(result::setErrorResult);

    return result;
  }

  @GetMapping("/service-test")
  public String serviceTest() {

    log.info("service test");
    jobService.serviceTest();

    return "service test";
  }
}