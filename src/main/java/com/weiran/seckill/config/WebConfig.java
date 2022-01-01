package com.weiran.seckill.config;

import com.weiran.seckill.interceptor.LimitInterceptor;
import com.weiran.seckill.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMVC 缺省配置
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    final LimitInterceptor limitInterceptor;

    final LoginInterceptor loginInterceptor;

    /**
     * 注册拦截器
     *
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(limitInterceptor)
                .addPathPatterns("/seckill/**");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/goods/**", "/seckill/**", "/order/**")
                .excludePathPatterns("/test", "/user/**", "/login",
                "/login.html", "/register", "/register.html", "/static/**");
    }

    /**
     * 配置静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
