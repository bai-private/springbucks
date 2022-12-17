package com.study.springbucks.controller;

import com.study.api.ExampleBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 远程测试接口
 * @author: 白剑民
 * @dateTime: 2022/12/17 15:40
 */
@Slf4j
@RestController
@RequestMapping("/remote")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RemoteController {

    private final ExampleBean exampleBean;

    /**
     * @description: 返回值为json的接口
     * @author: 白剑民
     * @date: 2022-12-17 15:42:10
     * @return: java.lang.String
     * @version: 1.0
     */
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExampleBean getJson() {
        return exampleBean;
    }

    /**
     * @description: 返回值为xml的接口
     * @author: 白剑民
     * @date: 2022-12-17 15:42:27
     * @return: java.lang.String
     * @version: 1.0
     */
    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ExampleBean getXml() {
        return exampleBean;
    }

}
