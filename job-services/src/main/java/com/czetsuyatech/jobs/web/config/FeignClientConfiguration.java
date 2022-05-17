package com.czetsuyatech.jobs.web.config;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
import com.czetsuyatech.jobs.config.ConditionalXrayEnabled;
import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

@ConditionalXrayEnabled
@Configuration(proxyBeanMethods = false)
public class FeignClientConfiguration {

//  @Bean
//  public Feign.Builder feignBuilder(HttpClientBuilder httpClientBuilder,
//      FeignHttpClientProperties httpClientProperties) {
//
//    //aws xray http client
//    CloseableHttpClient client = HttpClientBuilder.create().build();
//    return Feign.builder()
//        .retryer(Retryer.NEVER_RETRY)
//        .client(client(httpClientBuilder, httpClientProperties));
//  }

  @Bean
  public Client client(HttpClientBuilder httpClientBuilder, FeignHttpClientProperties httpClientProperties) {

    httpClientBuilder = httpClientBuilder != null ? httpClientBuilder : HttpClientBuilder.create();

    final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
        new PoolingHttpClientConnectionManager();
    poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
    poolingHttpClientConnectionManager.setMaxTotal(100);

    final RequestConfig defaultRequestConfig = RequestConfig.custom()
        .setConnectionRequestTimeout(100)
        .setConnectTimeout(httpClientProperties.getConnectionTimeout())
        .setSocketTimeout(100)
        .setRedirectsEnabled(httpClientProperties.isFollowRedirects())
        .build();

    return new ApacheHttpClient(
        httpClientBuilder
            .setConnectionManager(poolingHttpClientConnectionManager)
            .setDefaultRequestConfig(defaultRequestConfig)
            .build()
    );
  }

  @Bean
  public FeignFormatterRegistrar localDateFeignFormatterRegister() {

    return registry -> {
      DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
      registrar.setUseIsoFormat(true);
      registrar.registerFormatters(registry);
    };
  }
}
