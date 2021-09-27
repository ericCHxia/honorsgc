/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: PageSizeConfig.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/26 下午10:36
 */

package com.hdu.honor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.hdu.honor.page-size")
@Data
public class PageSizeConfig {
    Integer content = 5;
    Integer community = 20;
    Integer admin = 20;
}
