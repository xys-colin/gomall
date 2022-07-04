package cn.qmulin.gomall.order.to;

import cn.qmulin.gomall.order.entity.OrderEntity;
import cn.qmulin.gomall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/3 14:03
 */
@Data
public class OrderCreateTo {
    private OrderEntity order;

    private List<OrderItemEntity> orderItems;

    /** 订单计算的应付价格 **/
    private BigDecimal payPrice;

    /** 运费 **/
    private BigDecimal fare;
}
