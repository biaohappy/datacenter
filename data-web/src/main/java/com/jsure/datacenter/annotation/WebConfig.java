package com.jsure.datacenter.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/29
 * @Time: 16:58
 * I am a Code Man -_-!
 */
//@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestJsonArgumentResolver());
    }
}
