/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: PasswordInterceptorConfigure.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午11:24
 */

package com.hdu.honor.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PasswordInterceptorConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new PasswordInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/changepwd","/css/**","/js/**","/img/**","/logout","/redirection","/commitpwd");
    }
}
