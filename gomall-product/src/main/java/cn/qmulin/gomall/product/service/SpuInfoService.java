package cn.qmulin.gomall.product.service;

import cn.qmulin.gomall.product.vo.SpuSaveVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 01:06:10
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuInfo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);

    SpuInfoEntity getSpuInfoBySkuId(Long id);
}


