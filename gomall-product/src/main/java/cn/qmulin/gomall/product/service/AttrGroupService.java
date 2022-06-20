package cn.qmulin.gomall.product.service;

import cn.qmulin.gomall.product.entity.AttrEntity;
import cn.qmulin.gomall.product.vo.AttrGroupWithAttrsVo;
import cn.qmulin.gomall.product.vo.AttrRespVo;
import cn.qmulin.gomall.product.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-19 16:30:41
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {
    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
//
//   List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}


