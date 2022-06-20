package cn.qmulin.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/20 15:40
 */
@Data
public class SpuBoundTo {
    private long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
