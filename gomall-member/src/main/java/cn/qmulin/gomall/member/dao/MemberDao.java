package cn.qmulin.gomall.member.dao;

import cn.qmulin.gomall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:07:21
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
