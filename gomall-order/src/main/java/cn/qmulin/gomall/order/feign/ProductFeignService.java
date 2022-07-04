package cn.qmulin.gomall.order.feign;

import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 20:21
 */
@FeignClient("gomall-product")
public interface ProductFeignService {
    @GetMapping("/skuId/{id}")
    R getSpuInfoBySkuId(@PathVariable("id") Long id);
}
