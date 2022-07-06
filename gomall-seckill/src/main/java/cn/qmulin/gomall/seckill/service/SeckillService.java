package cn.qmulin.gomall.seckill.service;

import cn.qmulin.gomall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/6 16:09
 */
public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSkuSeckilInfo(Long skuId);

    String kill(String killId, String key, Integer num) throws InterruptedException;
}
