package com.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 远程调用工具Bean
 * @author: 白剑民
 * @dateTime: 2023/1/8 12:43
 */
@Slf4j
@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate AutoRestTemplate() {
        return new RestTemplate();
    }
}
