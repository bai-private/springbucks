package com.study.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @description: 消息接收者
 * @author: 白剑民
 * @dateTime: 2023/1/6 15:31
 */
@Slf4j
@Component
public class MessageReceiver {

    @StreamListener(Sink.INPUT)
    public void handler(Message<String> message) {
        log.info("接收到消息: {}", message.getPayload());
    }

}
