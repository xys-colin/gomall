package cn.qmulin.gomall.member.service;

import cn.qmulin.gomall.member.exception.PhoneException;
import cn.qmulin.gomall.member.exception.UsernameException;
import cn.qmulin.gomall.member.vo.MemberRegisterVo;
import cn.qmulin.gomall.member.vo.MemberUserLoginVo;
import cn.qmulin.gomall.member.vo.SocialUser;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:07:21
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo vo);
    void checkPhoneUnique(String phone) throws PhoneException;
    void checkUserNameUnique(String userName) throws UsernameException;

    MemberEntity login(MemberUserLoginVo vo);

    MemberEntity login(SocialUser vo) throws Exception;
}

