package com.czetsuyatech.jobs.web.config;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
import feign.Feign;
import feign.Retryer;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

//@Configuration
public class FeignClientConfiguration {

//  @Bean
//  public CloseableHttpClient feignClient() {
//    return HttpClients.createDefault();
//  }

  @Bean
  public Feign.Builder feignBuilder() {
    return Feign.builder()
        .retryer(Retryer.NEVER_RETRY)
        .client(new ApacheHttpClient(clientHttpRequestFactory().getHttpClient()));
  }

  private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
        HttpClientBuilder.create().useSystemProperties().build());
    factory.setReadTimeout(10000);
    factory.setConnectTimeout(2000);
    factory.setConnectionRequestTimeout(2000);
    return factory;
  }
}
