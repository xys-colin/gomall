package cn.qmulin.gomall.member.controller;

import java.util.Arrays;
import java.util.Map;

import cn.qmulin.common.exception.BizCodeEnum;
import cn.qmulin.gomall.member.exception.PhoneException;
import cn.qmulin.gomall.member.exception.UsernameException;
import cn.qmulin.gomall.member.feign.CouponFeignService;
import cn.qmulin.gomall.member.vo.MemberRegisterVo;
import cn.qmulin.gomall.member.vo.MemberUserLoginVo;
import cn.qmulin.gomall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.qmulin.gomall.member.entity.MemberEntity;
import cn.qmulin.gomall.member.service.MemberService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.common.utils.R;



/**
 * 会员
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:07:21
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponFeignService couponFeignService;

    @RequestMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R membercoupons = couponFeignService.membercoupons(); //假设张三去数据库查了后返回了张三的优惠券信息

        // 打印会员和优惠券信息
        return R.ok().put("member",memberEntity).put("coupons",membercoupons.get("coupons"));
    }


    @PostMapping(value = "/oauth2/login")
    public R oauthLogin(@RequestBody SocialUser socialUser) throws Exception {

        MemberEntity memberEntity = memberService.login(socialUser);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGIN_ACCOUNT_PASSWORD_INVALID.getCode(),BizCodeEnum.LOGIN_ACCOUNT_PASSWORD_INVALID.getMsg());
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo vo){
        try {
            memberService.register(vo);
        } catch (PhoneException e) {
            return R.error(BizCodeEnum.PHONE_EXISTS_EXCEPTION.getCode(),BizCodeEnum.PHONE_EXISTS_EXCEPTION.getMsg());
        } catch (UsernameException e) {
            return R.error(BizCodeEnum.USER_EXISTS_EXCEPTION.getCode(),BizCodeEnum.USER_EXISTS_EXCEPTION.getMsg());
        }

        return R.ok();
    }

    @PostMapping(value = "/login")
    public R login(@RequestBody MemberUserLoginVo vo) {

        MemberEntity memberEntity = memberService.login(vo);

        if (memberEntity != null) {
            return R.ok().put("data",memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGIN_ACCOUNT_PASSWORD_INVALID.getCode(),BizCodeEnum.LOGIN_ACCOUNT_PASSWORD_INVALID.getMsg());
        }
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
