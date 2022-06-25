package cn.qmulin.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/24 14:23
 */
@Data
public class SkuEsModel {
    private long skuId;
    private long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private long saleCount;
    private boolean hasStock;
    private long hotScore;
    private long brandId;
    private long catalogId;
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attrs> attrs;

    @Data
    public static class Attrs {
        private long attrId;
        private String attrName;
        private String attrValue;
    }
}
