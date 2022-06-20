/**
  * Copyright 2021 bejson.com 
  */
package cn.qmulin.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xys
 */
@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;


}