package com.wx.cloudprint.webserver.Interceptor;

import com.wx.cloudprint.webserver.anotation.Acess;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    // 在调用方法之前执行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 从方法处理器中获取出要调用的方法
            Method method = handlerMethod.getMethod();
            // 获取出方法上的Access注解
            Acess access = method.getAnnotation(Acess.class);
            if (access == null) {
                // 如果注解为null, 说明不需要拦截, 直接放过
                return true;
            }
            if (access.authorities().length > 0) {
                // 如果权限配置不为空, 则取出配置值
                if (request.getAttribute("user") == null) return false;

            }
            // 拦截之后应该返回公共结果, 这里没做处理
            return false;
        }else{
            return true;
        }
    }


}
