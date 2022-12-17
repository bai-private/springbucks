package com.study.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @description: 远程测试接口类
 * @author: 白剑民
 * @dateTime: 2022/12/17 15:40
 */
public interface RemoteRmi extends Remote {
    /**
     * @description: 基于rmi调用的返回json接口
     * @author: 白剑民
     * @date: 2022-12-17 16:57:22
     * @return: com.study.springbucks.beanconfig.ExampleBean
     * @version: 1.0
     */
    ExampleBean getJson() throws RemoteException;
    /**
     * @description: 基于rmi调用的返回xml接口
     * @author: 白剑民
     * @date: 2022-12-17 16:57:28
     * @return: com.study.springbucks.beanconfig.ExampleBean
     * @version: 1.0
     */
    ExampleBean getXml() throws RemoteException;
}
