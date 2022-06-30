package cn.qmulin.gomall.member.exception;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/29 18:19
 */
public class UsernameException extends RuntimeException {
    public UsernameException(){
        super("用户名存在");
    }
}
