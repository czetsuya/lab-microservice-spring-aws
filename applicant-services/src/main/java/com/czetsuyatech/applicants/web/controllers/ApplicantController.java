package com.czetsuyatech.applicants.web.controllers;

import io.github.resilience4j.retry.annotation.Retry;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ApplicantController {

  public static final long TIMEOUT = 300000L;

  @Value("${server.port}")
  private int port;

  @GetMapping("/profiles")
  public List<String> getApplicantsByJob() {

    log.debug("port={} get applicants by job", port);
    return Arrays.asList("Steve", "Bill", "Linus", InetAddress.getLoopbackAddress().getHostAddress());
  }

  @Retry(name = "default", fallbackMethod = "getTopApplicantsByJobDefault")
  // @CircuitBreaker(name = "default", fallbackMethod = "getTopApplicantsByJobDefault")
  @GetMapping("/top")
  public List<String> getTopApplicantsByJob() {

    log.debug("port={} get top applicants by job", port);

    ResponseEntity<String> result = new RestTemplate().getForEntity("http://localhost:8000/circuit-breaker",
        String.class);
    result.getBody();

    return null;
  }

  public List<String> getTopApplicantsByJobDefault(Exception e) {

    log.debug("port={} default top applicant", port);
    return Arrays.asList("Ed from circuit breaker");
  }

  @GetMapping("/thread-test")
  public String threadTest() {

    log.debug("thread test");

    return "application >> thread test";
  }
}
