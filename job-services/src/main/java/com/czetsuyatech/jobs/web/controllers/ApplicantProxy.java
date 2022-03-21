package com.czetsuyatech.jobs.web.controllers;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "applicant-services", url = "${app.ct.client.applicant.url}")
public interface ApplicantProxy {

  @GetMapping("/applicants/applicants-by-job")
  List<String> getApplicantsByJob();

  @GetMapping("/applicants/top-applicants-by-job")
  public List<String> getTopApplicantsByJob();
}