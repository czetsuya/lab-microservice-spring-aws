package com.czetsuyatech.jobs.configs;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalXrayEnabled
public class XRayHttpClientConfiguration {

  @Bean
  public HttpClientBuilder xrayHttpClientBuilder() {

    log.info("Setting up AWS xray http client configuration");
    return HttpClientBuilder.create();
  }
}
