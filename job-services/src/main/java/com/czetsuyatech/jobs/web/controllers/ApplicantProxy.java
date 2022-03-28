package com.czetsuyatech.jobs.web.controllers;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "applicant-services", url = "${app.ct.client.applicant.url}")
public interface ApplicantProxy {

  @GetMapping("/applicants/profiles")
  List<String> getApplicantsByJob();

  @GetMapping("/applicants/top")
  List<String> getTopApplicantsByJob();

  @GetMapping("/applicants/thread-test")
  String threadTest();
}
