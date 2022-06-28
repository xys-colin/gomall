package cn.qmulin.gomall.auth.controller;

import cn.qmulin.common.constant.AuthServerConstant;
import cn.qmulin.common.exception.BizCodeEnum;
import cn.qmulin.common.utils.R;
import cn.qmulin.gomall.auth.feign.MemberFeignService;
import cn.qmulin.gomall.auth.feign.ThirdPartyFeignService;
import cn.qmulin.gomall.auth.vo.UserLoginVo;
import cn.qmulin.gomall.auth.vo.UserRegisterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/28 20:59
 */
@Controller
public class LoginController {
    @Autowired
    private ThirdPartyFeignService feignService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MemberFeignService memberFeignService;
    @GetMapping("/sms/sendCode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone){
        //1、接口防刷
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.isNotBlank(redisCode)){
            //redis存的时间戳
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000){
                //1分钟内已给这个手机号发过短信，不能在发
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(),BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        //2、验证码再次校验，存redis ,key=phone,value=code
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        //redis中存储的验证码+时间戳
        String redisValue = code +"_"+System.currentTimeMillis();
        //redis缓存验证码，防止同一个手机号再次发送验证码
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone,redisValue,10, TimeUnit.MINUTES);
        feignService.sendCode(phone,code);
        return R.ok();
    }
    /**
     * 利用session原理，将数据放在session中，只要跳到下一个页面的取出这个数据以后，session里面的数据就会被删掉
     * @param vo
     * @param result
     * @param redirectAttributes: 模拟重定向携带数据
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            //校验出错，转发到注册页
            Map<String, String> map = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors",map);
            return "redirect:http://auth.gomall.com/reg.html";
        }
        //真正注册，调用远程服务注册
        //1、校验验证码
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (StringUtils.isNotBlank(s)){
            if (code.equals(s.split("_")[0])){
                //删除验证码;令牌机制
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                //验证码校验通过,真正注册，调用远程服务注册
                R r = memberFeignService.regist(vo);
                if (r.getCode()==0){
                    //成功,转到登录页
                    return "redirect:http://auth.gulimall.com/login.html";
                }else{
                    Map<String,Object> map = new HashMap<>();
                    map.put("msg",r.get("msg"));
                    System.out.println(map);
                    redirectAttributes.addFlashAttribute("errors",map);
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("code","验证码输入错误");
                redirectAttributes.addFlashAttribute("errors",map);
                //校验出错，转发到注册页
                return "redirect:http://auth.gulimall.com/reg.html";
            }
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("code","验证码输入错误");
            redirectAttributes.addFlashAttribute("errors",map);
            //校验出错，转发到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }
    }
//    @PostMapping("/login")
//    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes, HttpSession session){
//        //远程登录
//        R r = memberFeignService.login(vo);
//        if (r.getCode()==0){
//            MemberRespVo data = r.getData(new TypeReference<MemberRespVo>() {
//            });
//            session.setAttribute(AuthServerConstant.SESSION_LOGIN_KEY,data);
//            return "redirect:http://gulimall.com";
//        }else{
//            Map<String,String> errors = new HashMap<>();
//            errors.put("msg",r.getData("msg",new TypeReference<String>(){}));
//            redirectAttributes.addFlashAttribute("errors",errors);
//            return "redirect:http://auth.gulimall.com/login.html";
//        }
//    }

    @GetMapping("/login.html")
    public String loginPage(HttpSession session){
        Object attribute = session.getAttribute(AuthServerConstant.SESSION_LOGIN_KEY);
        if (attribute == null){
            //没登录去登录页
            return "login";
        }else {
            //已登录去首页
            return "redirect:http://gulimall.com";
        }
    }
}
