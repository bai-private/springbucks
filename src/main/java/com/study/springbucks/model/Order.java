package com.study.springbucks.model;

import com.study.springbucks.model.enums.OrderState;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 订单实体类
 *
 * @author baijianmin
 */
@Entity
@Table(name = "T_ORDER")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity implements Serializable {
    /**
     * 顾客名称
     */
    private String customer;
    /**
     * 当前订单状态
     */
    @Column(nullable = false)
    private OrderState state;
}
