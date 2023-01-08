package com.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: ZK注册中心-服务消费者启动类
 * @author: 白剑民
 * @dateTime: 2023/1/8 12:39
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerZKApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerZKApplication.class, args);
    }
}
