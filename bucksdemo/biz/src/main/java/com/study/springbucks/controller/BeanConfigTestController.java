package com.study.springbucks.controller;

import com.study.api.ExampleBean;
import com.study.springbucks.beanconfig.annotation.AnnotationBeanConfig;
import com.study.springbucks.beanconfig.xml.XmlBeanConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Bean装配测试
 * @author: 白剑民
 * @dateTime: 2022/12/17 15:10
 */
@Slf4j
@RestController
@RequestMapping("/bean/test")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BeanConfigTestController {

    // 以下两个注解都能实现自动注入装配，区别在于前者ByType(如果是接口，则寻找接口实现类)注入，后者ByName(类名或自定义Bean名称)注入
    // @Resource
    @Autowired
    private ExampleBean exampleBean;

    private final XmlBeanConfig xmlBeanConfig;

    private final AnnotationBeanConfig annotationBeanConfig;

    /**
     * @description: 测试xml装配
     * @author: 白剑民
     * @date: 2022-12-17 15:17:08
     * @version: 1.0
     */
    @GetMapping("/xml")
    public void getXmlBean() {
        xmlBeanConfig.context();
    }

    /**
     * @description: 测试注解装配
     * @author: 白剑民
     * @date: 2022-12-17 15:17:22
     * @version: 1.0
     */
    @GetMapping("/annotation")
    public void getAnnotationBean() {
        annotationBeanConfig.context();
    }

    /**
     * @description: 测试@Autowired或@Resource注入装配
     * @author: 白剑民
     * @date: 2022-12-17 15:17:22
     * @version: 1.0
     */
    @GetMapping("/autowired")
    public void getAutowiredBean() {
        log.info("我叫{},今年{}岁了", exampleBean.getName(), exampleBean.getAge());
    }

    /**
     * @description: 通过注解@Bean装配
     * @author: 白剑民
     * @date: 2022-12-17 15:27:41
     * @version: 1.0
     */
    @GetMapping("/bean")
    public void getBean() {
        log.info("我叫{},今年{}岁了", exampleBean.getName(), exampleBean.getAge());
    }

}
