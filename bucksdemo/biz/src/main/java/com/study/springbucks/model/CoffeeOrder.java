package com.study.springbucks.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 咖啡与订单关联关系实体
 *
 * @author baijianmin
 */
@Entity
@Table(name = "T_COFFEE_ORDER")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder extends BaseEntity implements Serializable {
    /**
     * 咖啡实体主键id
     */
    private Long coffeeId;
    /**
     * 订单实体主键id
     */
    private Long orderId;
}
