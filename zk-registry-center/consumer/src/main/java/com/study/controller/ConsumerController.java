package com.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @description: 服务消费者控制层
 * @author: 白剑民
 * @dateTime: 2023/1/8 12:42
 */
@Slf4j
@RestController
public class ConsumerController {
    private static final String URL = "http://provider-ZKpayment-module";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/test")
    public String getData() {
        return restTemplate.getForObject(URL + "/provider/test", String.class);
    }
}
