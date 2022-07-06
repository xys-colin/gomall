package cn.qmulin.gomall.seckill.feign;

import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/6 17:49
 */
@FeignClient("gomall-product")
public interface ProductFeignService {
    @RequestMapping("/product/spuinfo/info/{id}")
    R getSkuInfo(@PathVariable("id") Long id);
}
