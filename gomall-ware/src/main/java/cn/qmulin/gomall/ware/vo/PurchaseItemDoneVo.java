package cn.qmulin.gomall.ware.vo;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/20 18:05
 */
@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
