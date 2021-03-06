package cn.qmulin.gomall.product.dao;

import cn.qmulin.gomall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-19 16:30:41
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
