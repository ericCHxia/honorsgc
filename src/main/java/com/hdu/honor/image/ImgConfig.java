/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: imgConfig.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/9 下午9:19
 */

package com.hdu.honor.image;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@Configuration
@ConfigurationProperties(prefix = "com.hdu.honor.image")
@Data
public class ImgConfig {
    private String path;
    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize maxSize = DataSize.ofMegabytes(5);
    private Integer maxCoverWidth = 500;
    private Integer maxCoverHeight = 500;
    private Float coverQuality = 0.8f;
}
