package cn.qmulin.gomall.order.feign;

import cn.qmulin.gomall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 17:36
 */
@FeignClient("gomall-cart")
public interface CartFeignService {
    @GetMapping(value = "/currentUserCartItems")
    List<OrderItemVo> getCurrentCartItems();

}
