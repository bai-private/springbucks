package com.study.springbucks;

import com.github.pagehelper.PageInfo;
import com.study.springbucks.model.Coffee;
import com.study.springbucks.model.Order;
import com.study.springbucks.model.enums.OrderState;
import com.study.springbucks.service.CoffeeService;
import com.study.springbucks.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Collections;

/**
 * springbucks服务启动类
 *
 * @author baijianmin
 */
@Slf4j
@EnableTransactionManagement
@EnableCaching(proxyTargetClass = true)
@SpringBootApplication
@MapperScan("com.study.springbucks.mapper")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SpringBucksApplication implements ApplicationRunner {

    private final CoffeeService coffeeService;

    private final OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        /* task 1 */
        // 1、实现：CURD，增删改需要加入事务管理，可以通过注解或者切面

        // 增加订单
        log.info("咖啡信息: {}", coffeeService.findOneCoffee("latte"));
        Order insertResult = orderService.insert("客户1", coffeeService.findOneCoffee("latte"));
        Order insertResult2 = orderService.insert("客户2", coffeeService.findOneCoffee("espresso"));
        log.info("新增订单结果: {}", insertResult);

        // 修改订单状态
        boolean update = orderService.update(insertResult, OrderState.PAID);
        log.info("订单修改{}, 修改后的订单状态为: {}", update ? "成功" : "失败", insertResult.getState());

        // 2、查询时增加将结果缓存到 redis, 查询时需要实现翻页及根据主键批量查询
        // 查询订单并缓存
        // 页码
        int pageNum = 1;
        // 每页显示数量
        int pageSize = 1;
        PageInfo<Order> pageInfo =
                orderService.findByIds(Arrays.asList(insertResult.getId(), insertResult2.getId()), pageNum, pageSize);
        log.info("查询到的订单总数为: {}, 当前分页显示数据为: {}", pageInfo.getTotal(), pageInfo.getList());

        // 删除订单(将新增的订单删除)
        int deleteResult = orderService.delete(Collections.singletonList(insertResult.getId()));
        log.info("订单删除{}, 删除的订单ID为: {}", deleteResult == 0 ? "失败" : "成功", insertResult.getId());
    }
}
