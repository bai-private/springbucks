package com.study.springbucks.beanconfig.annotation;

import com.study.api.ExampleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 注解方式装配Bean
 * @author: 白剑民
 * @dateTime: 2022/12/17 15:01
 */
@Slf4j
@Configuration
@ComponentScan("com.study.springbucks.beanconfig")
public class AnnotationBeanConfig {

    public void context() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationBeanConfig.class);
        ExampleBean bean = context.getBean(ExampleBean.class);
        log.info("我叫{},今年{}岁了", bean.getName(), bean.getAge());
    }

    @Bean
    public ExampleBean getBean() {
        return new ExampleBean();
    }

}
