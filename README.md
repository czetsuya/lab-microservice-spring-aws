# Microservice Architecture with Spring

Microservice is a service-oriented architecture where an application is deployed as a collection of loosely-coupled 
services. The goal is to make each service independent, fine-grained, scalable and flexible which allows faster 
testing and release.

This project takes advantage of the AWS infrastructure and it's services such as:
 - ECR - docker registry
 - ECS - service orchestration for service deployment, management, scaling, monitoring, etc
 - AppMesh - service mesh that provides application level networking (normally used between internal services)
 - Load Balancer - use for external client connection

![Microservice Architecture with Spring](./docs/architecture.png)

## Our Microservices

### Business Services

applicant-services - Dummy service that returns a list of applicant names.

```java
@GetMapping("/applicants-by-job")
public List<String> getApplicantsByJob() {
 
    log.debug("port={} get applicants by job", port);
    return Arrays.asList("Steve", "Bill", "Linus");
}
```

job-services - Dummy service that returns a job title with a list of applicant names.

```java
@GetMapping("/job-with-applicant-profiles")
public ResponseEntity listJobsWithApplicantProfiles() {
 
    log.debug("get job details with applicants");
 
    JobWithApplicantsDto result = new JobWithApplicantsDto();
    result.setJob("Java Developer");
 
    result.setApplicants(applicantProxy.getApplicantsByJob());
 
    return ResponseEntity.ok().body(result);
}
```

## Spring Cloud Libraries

- spring-cloud-starter-openfeign - this library is used for referencing a service in the naming server

To enable this feature, @EnableFeignClients must be annotated to a configuration class.

To call an endpoint from another service, an interface must be created with the same method signature as the method from the other service. In this example, we are importing 2 endpoints from the applicant-service.

```java
@FeignClient(name = "applicant-services")
public interface ApplicantProxy {
 
    @GetMapping("/applicants/applicants-by-job")
    List<String> getApplicantsByJob();
 
    @GetMapping("/applicants/top-applicants-by-job")
    public List<String> getTopApplicantsByJob();
}
```

- spring-cloud-starter-sleuth - this library helps us trace our requests across different microservices by 
automatically adding a unique id to the logs.

As we can see in the logs, it started from api-gateway, pass thru job-services, and then application-services. In the 3 logs, notice the common unique id: 3b4039e47fb602a9. We can use this when tracing a request.

![Log trace](./docs/log_trace.jpg)

## Services URLs

### Applicant Services
http://localhost:8081/applicants/applicants-by-job
http://localhost:8081/applicants/top-applicants-by-job

### Job Services
http://localhost:8080/jobs/job-with-applicant-profiles
http://localhost:8080/jobs/job-with-top-applicants
