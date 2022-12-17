package com.study.springbucks;

import com.github.pagehelper.PageInfo;
import com.study.springbucks.model.Order;
import com.study.springbucks.model.enums.OrderState;
import com.study.springbucks.service.impl.CoffeeService;
import com.study.springbucks.service.impl.OnRemoteService;
import com.study.springbucks.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * springbucks服务启动类
 *
 * @author baijianmin
 */
@Slf4j
@EnableTransactionManagement
@EnableCaching(proxyTargetClass = true)
@SpringBootApplication
@ComponentScan("com.study")
@MapperScan("com.study.springbucks.mapper")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SpringBucksApplication implements ApplicationRunner {

    /**
     * 咖啡服务类
     */
    private final CoffeeService coffeeService;

    /**
     * 订单服务类
     */
    private final OrderService orderService;

    /**
     * Redis操作模板
     */
    private final StringRedisTemplate template;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBucksApplication.class, args);
        // 创建服务对象
        OnRemoteService remoteService = context.getBean(OnRemoteService.class);
        // 服务端RMI注册
        try {
            Registry registry = LocateRegistry.createRegistry(8081);
            // 绑定服务对象
            registry.bind("onRemoteService", remoteService);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
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
        PageInfo<Order> pageInfo2 =
                orderService.findByIds(Arrays.asList(insertResult.getId(), insertResult2.getId()), pageNum, pageSize);
        log.info("再查一次: {}", pageInfo2.getList());

        // 删除订单(将新增的订单删除)
        int deleteResult = orderService.delete(Collections.singletonList(insertResult.getId()));
        log.info("订单删除{}, 删除的订单ID为: {}", deleteResult == 0 ? "失败" : "成功", insertResult.getId());

        /* task 2 */
        // Redis基本数据结构命令练习记录
        // 以Markdown方式记录于项目根路径下的RedisCommandStudy.md中

        /* task 3 */
        // Redis集群搭建记录
        // 以Markdown方式记录于项目根路径下的BuildRedisCluster.md中

        /* task 4 */
        // 实现咖啡价格排名
        this.redisClusterForZset();

        // 实现全局ID
        long id = nextId("redis-uuid");
        log.info("redis实现全局ID: {}", id);
    }

    /**
     * 实现咖啡价格排名
     */
    private void redisClusterForZset() {
        // 定义zset的key
        String key = "coffee-price";
        // 定义元素对象列表
        List<DefaultTypedTuple<String>> coffees = new ArrayList<>();
        coffeeService.findAll().forEach(coffee -> {
            // 以咖啡名称作为元素名，以金额作为得分项
            DefaultTypedTuple<String> tuple =
                    new DefaultTypedTuple<>(coffee.getName(), coffee.getPrice().getAmount().doubleValue());
            // 加入元素列表
            coffees.add(tuple);
        });
        // 将元素列表放入zset集合中
        template.opsForZSet().add(key, new HashSet<>(coffees));
        // 按照排名先后(从小到大)打印指定区间内的元素, 即咖啡商品列表所有元素, -1为打印全部
        Set<String> range = template.opsForZSet().range(key, 0, -1);
        // 输出结果
        log.info("商品价格排序表: {}", range);
    }

    /**
     * 全局ID生成
     *
     * @param keyPrefix key前缀
     * @return Long ID
     */
    public long nextId(String keyPrefix) {
        //1 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timeStamp = nowSecond - 1640995200L;
        //2  生成序号
        //2.1 获取当前日期， 精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        Long count = template.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        //3  拼接返回
        assert count != null;
        return timeStamp << 32 | count;
    }

}
