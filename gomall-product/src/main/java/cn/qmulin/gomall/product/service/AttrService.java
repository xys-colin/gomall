package cn.qmulin.gomall.product.service;

import cn.qmulin.gomall.product.vo.AttrGroupRelationVo;
import cn.qmulin.gomall.product.vo.AttrRespVo;
import cn.qmulin.gomall.product.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-19 16:30:41
 */
public interface AttrService extends IService<AttrEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);


    /**
     * 在指定的所有属性集合里面，挑出检索属性
     * @param attrIds
     * @return
     */
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

