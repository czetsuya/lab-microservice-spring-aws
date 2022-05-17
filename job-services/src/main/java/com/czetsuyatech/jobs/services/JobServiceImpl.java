package com.czetsuyatech.jobs.services;

import com.czetsuyatech.jobs.web.controllers.ApplicantProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class JobServiceImpl implements JobService {

  private final ApplicantProxy applicantProxy;

  @Override
  public void serviceTest() {

    log.info("service test");
    applicantProxy.threadTest();
  }
}