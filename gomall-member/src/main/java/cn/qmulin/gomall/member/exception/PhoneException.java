package cn.qmulin.gomall.member.exception;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/29 18:19
 */
public class PhoneException extends RuntimeException {
    public PhoneException(){
        super("手机号存在");
    }
}
