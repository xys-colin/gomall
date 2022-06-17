package cn.qmulin.gomall.order.dao;

import cn.qmulin.gomall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:19:58
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
