package cn.qmulin.common.to.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/4 16:33
 */
@Data
public class StockDetailTo implements Serializable {

    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 购买个数
     */
    private Integer skuNum;
    /**
     * 工作单id
     */
    private Long taskId;

    /**
     * 仓库id
     */
    private Long wareId;

    /**
     * 锁定状态
     */
    private Integer lockStatus;
}
