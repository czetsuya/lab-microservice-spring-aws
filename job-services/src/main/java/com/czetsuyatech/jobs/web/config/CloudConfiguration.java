package com.czetsuyatech.jobs.web.config;

import com.czetsuyatech.jobs.Application;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {Application.class})
public class CloudConfiguration {

}
