package cn.qmulin.gomall.auth.vo;

import lombok.Data;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/29 22:22
 */
@Data
public class SocialUser {
    private String access_token;
    private String remind_in;
    private long expires_in;
    private String uid;
    private String isRealName;
}

