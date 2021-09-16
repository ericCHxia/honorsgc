/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: ImageUploadResponse.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/10 下午1:03
 */

package com.hdu.honor.image;

import lombok.Data;

@Data
public class ImageUploadResponse {
    private Integer success;
    private String message;
    private String url;
}
