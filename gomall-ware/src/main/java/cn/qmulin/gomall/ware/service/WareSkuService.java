package cn.qmulin.gomall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.ware.entity.WareSkuEntity;

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
}
