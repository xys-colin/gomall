package cn.qmulin.gomall.ware.service;

import cn.qmulin.common.to.mq.OrderTo;
import cn.qmulin.common.to.mq.StockLockedTo;
import cn.qmulin.common.vo.SkuHasStockVo;
import cn.qmulin.gomall.ware.vo.WareSkuLockVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:24:52
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    Boolean orderLockStock(WareSkuLockVo wareSkuLockVo);

    void unlockStock(StockLockedTo to );

    void unlockStock(OrderTo orderTo);
}

