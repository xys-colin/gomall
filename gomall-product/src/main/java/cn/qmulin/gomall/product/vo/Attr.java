/**
  * Copyright 2021 bejson.com 
  */
package cn.qmulin.gomall.product.vo;

import lombok.Data;
import lombok.ToString;


/**
 * @author xys
 */
@ToString
@Data
public class Attr {

    private Long attrId;
    private String attrName;
    private String attrValue;

}