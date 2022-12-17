package com.study.springbucks.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.springbucks.mapper.CoffeeOrderMapper;
import com.study.springbucks.mapper.OrderMapper;
import com.study.springbucks.model.Coffee;
import com.study.springbucks.model.CoffeeOrder;
import com.study.springbucks.model.Order;
import com.study.springbucks.model.enums.OrderState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 咖啡订单服务类
 *
 * @author baijianmin
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "order")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderService {

    private final OrderMapper orderMapper;

    private final CoffeeOrderMapper coffeeOrderMapper;

    /**
     * 创建订单
     *
     * @param customer 客户名称
     * @param coffee   咖啡实体列表
     * @return 订单实体
     */
    public Order insert(String customer, Coffee... coffee) {
        // 创建订单
        Order order = Order.builder()
                .customer(customer)
                .state(OrderState.INIT)
                .build();
        orderMapper.insert(order);
        // 定义咖啡与订单的关联关系列表
        List<CoffeeOrder> coffeeOrders = new ArrayList<>();
        // 遍历咖啡实体列表
        Arrays.stream(coffee).forEach(c -> {
            // 创建关联关系
            CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                    .orderId(order.getId())
                    .coffeeId(c.getId())
                    .build();
            // 加入关联关系列表
            coffeeOrders.add(coffeeOrder);
        });
        // 新增咖啡实体与订单绑定
        coffeeOrderMapper.insert(coffeeOrders);
        return order;
    }

    /**
     * 删除订单
     *
     * @param ids 订单实体主键id列表
     */
    public int delete(List<Long> ids) {
        return orderMapper.delete(ids);
    }

    /**
     * 更新订单
     *
     * @param order 订单实体
     * @param state 订单状态枚举
     * @return boolean
     */
    public boolean update(Order order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderMapper.insert(order);
        log.info("Updated Order: {}", order);
        return true;
    }

    /**
     * 根据订单实体主键id批量查询订单
     *
     * @param ids      订单实体主键id
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @return PageInfo<Order> 分页信息
     */
    @Cacheable
    public PageInfo<Order> findByIds(List<Long> ids, int pageNum, int pageSize) {
        // PageHelper的静态startPage方法可对该行代码下的第一个sql语句进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.findByIds(ids);
        return new PageInfo<>(orderList);
    }
}
