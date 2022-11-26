package com.study.springbucks.mapper;

import com.study.springbucks.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单的SQLMapper映射
 *
 * @author baijianmin
 */
@Repository
public interface OrderMapper {

    /**
     * 新增订单
     *
     * @param order 订单实体
     * @return 订单实体主键id
     */
    Long insert(@Param("order") Order order);

    /**
     * 单个或批量删除订单
     *
     * @param ids 订单实体主键id列表
     * @return 被删除的实体数量
     */
    int delete(@Param("ids") List<Long> ids);

    /**
     * 单个或批量修改订单
     *
     * @param list 订单实体列表
     * @return 被修改的实体数量
     */
    int update(@Param("list") List<Order> list);

    /**
     * 根据订单实体主键id列表查询订单
     *
     * @param ids 订单实体主键id
     * @return 订单实体列表
     */
    List<Order> findByIds(@Param("ids") List<Long> ids);

}
