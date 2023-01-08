package com.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @description: 服务提供者控制层
 * @author: 白剑民
 * @dateTime: 2023/1/8 12:41
 */
@Slf4j
@RestController
public class ProviderController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/provider/test")
    public String test() {
        return "提供服务模块服务端口号为:" + serverPort;
    }

}
