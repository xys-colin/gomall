package cn.qmulin.gomall.product.feign;

import cn.qmulin.common.to.es.SkuEsModel;
import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/24 22:31
 */
@FeignClient("gomall-search")
public interface SearchFeignService {
    @PostMapping("/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
