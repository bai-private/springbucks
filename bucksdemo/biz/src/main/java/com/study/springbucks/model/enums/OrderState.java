package com.study.springbucks.model.enums;

/**
 * 订单状态
 *
 * @author baijianmin
 */
public enum OrderState {
    /**
     * 订单初始化
     */
    INIT,
    /**
     * 订单已支付
     */
    PAID,
    /**
     * 订单制作中
     */
    BREWING,
    /**
     * 订单制作完成
     */
    BREWED,
    /**
     * 订单已取
     */
    TAKEN,
    /**
     * 订单被取消
     */
    CANCELLED
}
