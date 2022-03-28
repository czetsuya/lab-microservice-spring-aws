package com.czetsuyatech.jobs.config;

import com.amazonaws.xray.contexts.SegmentContextExecutors;
import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class XRayAsyncExecutorBeans {

  public XRayAsyncExecutorBeans() {

  }

  @Bean
  @Primary
  public Executor xrayExecutor() {
    return SegmentContextExecutors.newSegmentContextExecutor();
  }
}