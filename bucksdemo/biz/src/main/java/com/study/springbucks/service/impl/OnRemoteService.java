package com.study.springbucks.service.impl;

import com.study.api.ExampleBean;
import com.study.api.RemoteRmi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @description: 基于RMI远程调用的服务端接口服务类
 * @author: 白剑民
 * @dateTime: 2022/12/17 16:59
 */
@Slf4j
@Service
public class OnRemoteService extends UnicastRemoteObject implements RemoteRmi {

    private final ExampleBean exampleBean;

    public OnRemoteService(ExampleBean exampleBean) throws RemoteException {
        super();
        this.exampleBean = exampleBean;
    }

    @Override
    public ExampleBean getJson() throws RemoteException {
        return exampleBean;
    }

    @Override
    public ExampleBean getXml() throws RemoteException {
        return exampleBean;
    }
}
