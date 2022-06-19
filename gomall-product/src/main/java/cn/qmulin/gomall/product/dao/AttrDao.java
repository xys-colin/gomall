package cn.qmulin.gomall.product.dao;

import cn.qmulin.gomall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品属性
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-19 16:30:41
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrIds(List<Long> attrIds);
}
