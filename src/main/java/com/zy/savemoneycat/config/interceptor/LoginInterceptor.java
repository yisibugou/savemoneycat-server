package com.zy.savemoneycat.config.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
//        HandlerMethod m = (HandlerMethod) handler;
//
//        System.out.println(m.getMethod().getName());
        if(!StringUtils.hasLength(token)) {
            System.out.println("没有登录");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("没有登录");
            return false;
        }
        return true;
    }
}
