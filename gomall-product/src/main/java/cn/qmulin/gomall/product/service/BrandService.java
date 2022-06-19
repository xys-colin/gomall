package cn.qmulin.gomall.product.service;

import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.product.entity.BrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author yaoxinjia
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);

    List<BrandEntity> getBrandsByIds(List<Long> brandIds);
}

