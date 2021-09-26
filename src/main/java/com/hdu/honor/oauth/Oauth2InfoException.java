/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2InfoException.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午9:26
 */

package com.hdu.honor.oauth;

public class Oauth2InfoException extends Exception{
    public Oauth2InfoException() {
    }

    public Oauth2InfoException(String message) {
        super(message);
    }
}
