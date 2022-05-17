package com.czetsuyatech.applicants.configs;

import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.strategy.SegmentNamingStrategy;
import java.util.Optional;
import javax.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalXrayEnabled
public class XRayWebFilterConfiguration {

  @Value("${spring.application.name}")
  private String AWS_XRAY_SEGMENT_NAME;

  @Bean
  public Filter tracingFilter(final AWSXRayRecorder awsxRayRecorder) {

    log.info("Setting up AWS Xray tracing filter");
    return new AWSXRayServletFilter(SegmentNamingStrategy.dynamic(Optional.ofNullable(AWS_XRAY_SEGMENT_NAME).orElse(
        "xray-filter")));
  }
}
