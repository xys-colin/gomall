package cn.qmulin.gomall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;
/**
 * @author xys
 */
@ToString
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}
