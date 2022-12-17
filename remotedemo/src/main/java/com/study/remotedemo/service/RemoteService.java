package com.study.remotedemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @description: 远程接口服务类
 * @author: 白剑民
 * @dateTime: 2022/12/17 16:43
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RemoteService {

    public static void askForRmi() {
        Registry registry = LocateRegistry.getRegistry("localhost", 8081);
         hello = (Hello) registry.lookup("Hello");
        hello.printMessage("Hello, World!");
    }

    public static void askForClient() {

    }

}
