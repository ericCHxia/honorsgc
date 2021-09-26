/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2Config.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午7:49
 */

package com.hdu.honor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.hdu.honor.oauth2.client")
@Data
public class Oauth2Config {
    String id;
    String secret;
    String state;
}
