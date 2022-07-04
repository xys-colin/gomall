package cn.qmulin.gomall.ware.feign;

import cn.qmulin.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 21:57
 */
@FeignClient("gomall-member")
public interface MemberFeignService {
    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    R addressInfo(@PathVariable("id") Long id);
}
