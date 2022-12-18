package com.study.remotedemo.service;

import com.alibaba.fastjson.JSON;
import com.study.api.ExampleBean;
import com.study.api.RemoteRmi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.util.Objects;

/**
 * @description: 远程接口服务类
 * @author: 白剑民
 * @dateTime: 2022/12/17 16:43
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RemoteService {

    /**
     * 请求接口(xml同，切换请求头即可)
     */
    private final static String REMOTE_API = "http://localhost:8080/remote/json";

    private final RestTemplate restTemplate;

    /**
     * @description: 通过Okhttp客户端进行远程接口调用
     * @author: 白剑民
     * @date: 2022-12-18 12:34:41
     * @version: 1.0
     */
    public static void requestForOkhttp() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                // 设置连接超时时间
                .connectTimeout(Duration.ofSeconds(30))
                // 设置读超时时间
                .readTimeout(Duration.ofSeconds(60))
                // 设置写超时时间
                .writeTimeout(Duration.ofSeconds(60))
                // 设置完整请求超时时间
                .callTimeout(Duration.ofSeconds(120))
                // 添加一个拦截器
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    return chain.proceed(request);
                })
                // 注册事件监听器
                .eventListener(new EventListener() {
                    @Override
                    public void callEnd(@NotNull Call call) {
                        super.callEnd(call);
                    }
                }).build();
        // 构造一个 Request 对象
        Request request = new Request.Builder()
                // 标识为 GET 请求
                .get()
                // 设置请求路径
                .url(REMOTE_API)
                // 添加头信息
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();
        // 通过 HttpClient 把 Request 构造为 Call 对象
        Call call = httpClient.newCall(request);

        // 执行同步请求
        try {
            Response response = call.execute();
            // 判断请求结果
            if (response.isSuccessful()) {
                log.info("Okhttp接口请求结果为 {}", Objects.requireNonNull(response.body()).string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 异步请求
        // call.enqueue(new Callback() {
        //     @Override
        //     public void onFailure(@NotNull Call call, @NotNull IOException e) {
        //         log.error("Okhttp接口请求异常 {}", e.getMessage());
        //     }
        //
        //     @Override
        //     public void onResponse(@NotNull Call call, @NotNull Response response) {
        //         String body = Objects.requireNonNull(response.body()).toString();
        //         log.info("Okhttp接口请求结果为 {}", body);
        //     }
        // });

    }

    /**
     * @description: 使用springboot的RestTemplate进行远程接口调用
     * @author: 白剑民
     * @date: 2022-12-17 19:05:56
     * @version: 1.0
     */
    public void requestForRestTemplate() {
        ResponseEntity<ExampleBean> response = restTemplate.getForEntity(REMOTE_API, ExampleBean.class);
        // 判断响应状态码
        int statusCode = response.getStatusCode().value();
        if (statusCode == HttpStatus.SC_OK) {
            ExampleBean body = response.getBody();
            log.info("RestTemplate接口请求结果: {}", JSON.toJSONString(body));
        } else {
            log.error("接口请求失败，错误码: {}", statusCode);
        }
    }

    /**
     * @description: 使用java原生的URLConnection进行远程接口调用
     * @author: 白剑民
     * @date: 2022-12-17 18:55:19
     * @version: 1.0
     */
    public static void requestForUrl() {
        try {
            URL url = new URL(REMOTE_API);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            log.info("原生URLConnection接口请求结果: {}", response);
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 使用apache的HttpClient类来进行远程接口调用
     * @author: 白剑民
     * @date: 2022-12-17 18:44:30
     * @version: 1.0
     */
    public static void requestForHttpClient() {
        // 创建 HttpClient 对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建 HttpGet 对象
        HttpGet httpGet = new HttpGet(REMOTE_API);
        // 设置json请求头
        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 发送请求并获取响应
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 判断响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String body = EntityUtils.toString(response.getEntity());
                log.info("HttpClient接口请求结果: {}", body);
            } else {
                log.error("接口请求失败，错误码: {}", statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 通过远程方法调用(RMI)进行接口方法访问
     * @author: 白剑民
     * @date: 2022-12-17 18:12:47
     * @version: 1.0
     */
    public static void requestForRmi() {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry("localhost", 8081);
            RemoteRmi r = (RemoteRmi) registry.lookup("onRemoteService");
            log.info("远程方法调用(RMI)结果[json]: {}", JSON.toJSONString(r.getJson()));
            log.info("远程方法调用(RMI)结果[xml]: {}", JSON.toJSONString(r.getXml()));
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        requestForOkhttp();
    }
}
