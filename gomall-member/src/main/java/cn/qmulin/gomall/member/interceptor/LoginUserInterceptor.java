package cn.qmulin.gomall.member.interceptor;

import cn.qmulin.common.constant.AuthServerConstant;
import cn.qmulin.common.vo.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 拦截用户是否登录
 * @author: xys
 * @date: 2022/7/2 15:59
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    public static ThreadLocal<MemberRespVo> loginUser=new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        boolean match = new AntPathMatcher().match("/member/**", uri);
        if (match) return true;
        MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.SESSION_LOGIN_KEY);
        if (attribute != null) {
            loginUser.set(attribute);
            return true;
        }else {
            request.getSession().setAttribute("msg","请先登录");
            response.sendRedirect("http://auth.gomall.com.login.html");
            return false;
        }
    }
}
