package cn.qmulin.gomall.product.vo;

import lombok.Data;

import java.util.List;
/**
 * @author xys
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
