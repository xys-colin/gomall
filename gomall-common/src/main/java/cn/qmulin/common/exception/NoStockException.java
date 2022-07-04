package cn.qmulin.common.exception;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/3 16:34
 */
public class NoStockException extends RuntimeException {
    public NoStockException(Long skuId) {
        super("商品id:"+skuId+";没有足够的库存了");
    }

    public NoStockException(String message) {
        super("商品"+message);
    }
}
