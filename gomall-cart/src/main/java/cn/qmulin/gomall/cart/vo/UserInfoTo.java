package cn.qmulin.gomall.cart.vo;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/30 23:05
 */
@Data
public class UserInfoTo {
    private Long userId;
    private String userKey;
    private Boolean tempUser;
}
