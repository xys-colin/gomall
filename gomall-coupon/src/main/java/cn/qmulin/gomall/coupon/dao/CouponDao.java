package cn.qmulin.gomall.coupon.dao;

import cn.qmulin.gomall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 10:51:50
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
