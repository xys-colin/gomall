package cn.qmulin.gomall.member.feign;

import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/5 15:37
 */
@FeignClient("gomall-order")
public interface OrderFeignService {
    @PostMapping("/order/order/listWithItem")
    R listWithItem(@RequestBody Map<String, Object> params);
}
