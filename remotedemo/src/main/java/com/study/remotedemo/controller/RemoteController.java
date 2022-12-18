package com.study.remotedemo.controller;

import com.study.remotedemo.service.RemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 远程接口控制层
 * @author: 白剑民
 * @dateTime: 2022/12/17 16:43
 */
@Slf4j
@RestController
@RequestMapping("/remote")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RemoteController {

    private final RemoteService remoteService;

}
