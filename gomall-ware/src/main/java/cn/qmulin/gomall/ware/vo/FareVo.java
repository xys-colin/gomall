package cn.qmulin.gomall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 22:11
 */
@Data
public class FareVo {
    private MemberAddressVo addressVo;
    private BigDecimal fare;
}
