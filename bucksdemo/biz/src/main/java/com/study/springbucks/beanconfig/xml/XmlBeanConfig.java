package com.study.springbucks.beanconfig.xml;

import com.study.api.ExampleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @description: XML方式装配Bean
 * @author: 白剑民
 * @dateTime: 2022/12/17 15:01
 */
@Slf4j
@Service
public class XmlBeanConfig {

    public void context() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        ExampleBean bean = context.getBean(ExampleBean.class);
        log.info("我叫{},今年{}岁了", bean.getName(), bean.getAge());
    }

}
