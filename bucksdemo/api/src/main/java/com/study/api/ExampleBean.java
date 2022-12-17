package com.study.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: Bean示例
 * @author: 白剑民
 * @dateTime: 2022/12/17 15:03
 */
@Slf4j
@Data
@Component
public class ExampleBean {

    /* 使用注解@Value同样能注入默认属性，当xml也配置了value属性时，则以xml配置为准 */

    @Value("注解注入名称")
    private String name;

    @Value("10086")
    private Integer age;

}
