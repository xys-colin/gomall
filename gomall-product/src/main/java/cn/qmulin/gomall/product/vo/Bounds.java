/**
  * Copyright 2021 bejson.com 
  */
package cn.qmulin.gomall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xys
 */
@Data
public class Bounds {

    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}