package cn.qmulin.common.vo;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/24 15:27
 */
@Data
public class SkuHasStockVo {
    private long skuId;
    private boolean hasStock;
}
