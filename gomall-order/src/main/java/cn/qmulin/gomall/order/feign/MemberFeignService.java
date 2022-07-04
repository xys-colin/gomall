package cn.qmulin.gomall.order.feign;

import cn.qmulin.gomall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 16:24
 */
@FeignClient("gomall-member")
public interface MemberFeignService {

    @GetMapping(value = "/member/memberreceiveaddress/{memberId}/address")
    List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);
}
