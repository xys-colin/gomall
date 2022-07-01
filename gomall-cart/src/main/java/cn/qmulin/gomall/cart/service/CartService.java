package cn.qmulin.gomall.cart.service;

import cn.qmulin.gomall.cart.vo.CartItemVo;
import cn.qmulin.gomall.cart.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/30 22:26
 */
public interface CartService {
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
    CartItemVo getCartItem(Long skuId);
    CartVo getCart() throws ExecutionException, InterruptedException;
    void clearCartInfo(String cartKey);
    void checkItem(Long skuId, Integer check);
    void changeItemCount(Long skuId, Integer num);
    void deleteIdCartInfo(Integer skuId);
    List<CartItemVo> getUserCartItems();
}
