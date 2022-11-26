package com.study.springbucks.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.springbucks.mapper.CoffeeMapper;
import com.study.springbucks.model.Coffee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 咖啡服务类
 *
 * @author baijianmin
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "coffee")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CoffeeService {

    private final CoffeeMapper coffeeMapper;

    /**
     * 根据咖啡实体主键id批量查询咖啡商品项
     *
     * @param ids      咖啡实体主键id
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @return PageInfo<Coffee> 分页信息
     */
    @Cacheable
    public PageInfo<Coffee> findByIds(List<Long> ids, int pageNum, int pageSize) {
        // PageHelper的静态startPage方法可对该行代码下的第一个sql语句进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<Coffee> coffeeList = coffeeMapper.findByIds(ids);
        return new PageInfo<>(coffeeList);
    }

    /**
     * 根据咖啡名称查找咖啡商品项
     *
     * @param name 咖啡名称
     * @return 咖啡实体
     */
    @Cacheable
    public Coffee findOneCoffee(String name) {
        Coffee coffee = coffeeMapper.findByName(name);
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }
}
