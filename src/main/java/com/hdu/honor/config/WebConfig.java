/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: WebConfig.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/9 下午9:04
 */

package com.hdu.honor.config;

import com.hdu.honor.image.ImgConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private ImgConfig imgConfig;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("配置文件已经生效");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+imgConfig.getPath());
    }
}


