package cn.qmulin.gomall.coupon.dao;

import cn.qmulin.gomall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 10:51:49
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
