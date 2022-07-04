package cn.qmulin.gomall.ware.vo;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/3 16:06
 */
@Data
public class LockStockResult {
    private Long skuId;
    private Integer num;
    private Boolean locked;
}
