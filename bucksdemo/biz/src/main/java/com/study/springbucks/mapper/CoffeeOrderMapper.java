package com.study.springbucks.mapper;

import com.study.springbucks.model.CoffeeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单的SQLMapper映射
 *
 * @author baijianmin
 */
@Repository
public interface CoffeeOrderMapper {

    /**
     * 新增订单
     *
     * @param list 订单实体
     * @return 订单实体
     */
    Long insert(@Param("list") List<CoffeeOrder> list);

}
