package cn.qmulin.gomall.order.vo;

import cn.qmulin.gomall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/3 11:31
 */
@Data
public class SubmitOrderResponseVo {
    //订单记录
    private OrderEntity order;
    //0成功，错误状态码
    private Integer code;

}
