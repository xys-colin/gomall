package cn.qmulin.gomall.product.service;

import cn.qmulin.gomall.product.vo.AttrGroupRelationVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-19 16:30:41
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void saveBatch(List<AttrGroupRelationVo> vos);
}

