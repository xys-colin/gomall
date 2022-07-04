package cn.qmulin.gomall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/3 15:56
 */
@Data
public class WareSkuLockVo {
    private String orderSn;

    /** 需要锁住的所有库存信息 **/
    private List<OrderItemVo> locks;
}
