package com.study.springbucks.mapper;

import com.study.springbucks.model.Coffee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 咖啡的SQLMapper映射
 *
 * @author baijianmin
 */
@Repository
public interface CoffeeMapper {

    /**
     * 新增咖啡商品项
     *
     * @param coffee 咖啡实体
     * @return 咖啡实体
     */
    Coffee insert(@Param("coffee") Coffee coffee);

    /**
     * 单个或批量删除咖啡商品项
     *
     * @param ids 咖啡实体主键id列表
     * @return 被删除的实体数量
     */
    int delete(@Param("ids") List<Long> ids);

    /**
     * 单个或批量修改咖啡商品项
     *
     * @param list 咖啡实体列表
     * @return 被修改的实体数量
     */
    int update(@Param("list") List<Coffee> list);

    /**
     * 根据咖啡实体主键id列表查询咖啡商品项
     *
     * @param ids 咖啡实体主键id
     * @return 咖啡商品项列表
     */
    List<Coffee> findByIds(@Param("ids") List<Long> ids);

    /**
     * 根据咖啡名称查找咖啡商品项
     *
     * @param name 咖啡名称
     * @return 咖啡实体
     */
    Coffee findByName(@Param("name") String name);

    /**
     * 查找所有咖啡商品项
     *
     * @return 咖啡实体列表
     */
    List<Coffee> findAll();
}
