package cn.qmulin.gomall.seckill.feign;

import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/6 16:13
 */
@FeignClient("gomall-coupon")
public interface CouponFeignService {
    @GetMapping("/coupon/seckillsession/lates3DaySession")
    R getLates3DaySession();
}
