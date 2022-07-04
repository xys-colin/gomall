package cn.qmulin.gomall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/3 14:28
 */
@Data
public class FareVo {
    private MemberAddressVo address;
    private BigDecimal fare;
}
