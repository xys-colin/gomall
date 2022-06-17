package cn.qmulin.gomall.order.dao;

import cn.qmulin.gomall.order.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:19:58
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
