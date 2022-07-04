package cn.qmulin.gomall.order.vo;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 20:52
 */
@Data
public class SkuStockVo {

    private Long skuId;

    private Boolean hasStock;
}
