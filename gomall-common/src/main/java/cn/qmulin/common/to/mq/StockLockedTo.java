package cn.qmulin.common.to.mq;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/4 16:32
 */
@Data
public class StockLockedTo {
    /** 库存工作单的id **/
    private Long id;

    /** 工作单详情的所有信息 **/
    private StockDetailTo detailTo;
}
