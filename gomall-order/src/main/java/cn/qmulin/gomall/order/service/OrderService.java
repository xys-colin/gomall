package cn.qmulin.gomall.order.service;

import cn.qmulin.gomall.order.vo.OrderConfirmVo;
import cn.qmulin.gomall.order.vo.OrderSubmitVo;
import cn.qmulin.gomall.order.vo.SubmitOrderResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.order.entity.OrderEntity;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:19:58
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);
    OrderEntity getOrderByOrderSn(String orderSn);
    void closeOrder(OrderEntity orderEntity);

}

