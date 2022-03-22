package com.czetsuyatech.applicants.web.config;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(clientHttpRequestFactory());
  }

  private ClientHttpRequestFactory clientHttpRequestFactory() {

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
        HttpClientBuilder.create().useSystemProperties().build());
    factory.setReadTimeout(10000);
    factory.setConnectTimeout(2000);
    factory.setConnectionRequestTimeout(2000);
    return factory;
  }
}
