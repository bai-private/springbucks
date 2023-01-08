package com.study;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: apollo启动类
 * @author: 白剑民
 * @dateTime: 2023/1/8 12:17
 */
@Slf4j
@SpringBootApplication
@EnableApolloConfig
public class ApolloApplication {
    public static void main(String[] args) {
        System.setProperty("env", "DEV");
        SpringApplication.run(ApolloApplication.class, args);
    }
}
