package cn.qmulin.gomall.ware.dao;

import cn.qmulin.gomall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:24:52
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
