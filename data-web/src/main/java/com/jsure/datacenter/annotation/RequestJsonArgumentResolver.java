package com.jsure.datacenter.annotation;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * @Author: wuxiaobiao
 * @Description: 自定义获取json参数注解
 * @Date: Created in 2018/5/29
 * @Time: 14:48
 * I am a Code Man -_-!
 */
@Slf4j
public class RequestJsonArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestParamLog.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
        RequestParamLog requestParamLog = methodParameter.getParameterAnnotation(RequestParamLog.class);
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int rd;
        while ((rd = reader.read(buf)) != -1) {
            sb.append(buf, 0, rd);
        }
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        log.info(request.getRequestURI()+",param: {}", jsonObject);
        return jsonObject;
    }
}
