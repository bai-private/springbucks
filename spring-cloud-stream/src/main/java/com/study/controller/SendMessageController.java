package com.study.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试消息发送控制层
 * @author: 白剑民
 * @dateTime: 2023/1/6 15:26
 */
@Slf4j
@RestController
@AllArgsConstructor
public class SendMessageController {

    private final Source source;

    /**
     * @param message 消息体
     * @description: 发送MQ消息
     * @author: 白剑民
     * @date: 2023-01-06 15:30:32
     * @version: 1.0
     */
    @PostMapping(value = "sendMsg")
    public void sendMsg(@RequestParam String message) {
        Message<String> msg = MessageBuilder.withPayload(message).build();
        source.output().send(msg);
    }

}
