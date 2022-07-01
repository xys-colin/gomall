package cn.qmulin.gomall.cart.exception;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/1 17:02
 */
public class CartExceptionHandler extends RuntimeException {
    public CartExceptionHandler(){
        super("购物车异常");
    }
}
