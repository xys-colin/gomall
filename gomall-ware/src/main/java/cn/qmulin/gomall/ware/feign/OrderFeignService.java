package cn.qmulin.gomall.ware.feign;

import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/4 17:27
 */
@FeignClient("gomall-order")
public interface OrderFeignService {
    @GetMapping("/order/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);
}
