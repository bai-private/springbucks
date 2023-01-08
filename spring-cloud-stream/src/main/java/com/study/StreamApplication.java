package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @description: SpringCloudStream启动类
 * @author: 白剑民
 * @dateTime: 2023/1/6 15:17
 */
@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
public class StreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }
}
