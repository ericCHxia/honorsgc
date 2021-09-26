/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2Response.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午8:48
 */

package com.hdu.honor.oauth;

public class Oauth2TokenResponse {
    private boolean cache;
    private Oauth2TokenData data;
    private int error;
    private String msg;
    public void setCache(boolean cache) {
        this.cache = cache;
    }
    public boolean getCache() {
        return cache;
    }

    public void setData(Oauth2TokenData data) {
        this.data = data;
    }
    public Oauth2TokenData getData() {
        return data;
    }

    public void setError(int error) {
        this.error = error;
    }
    public int getError() {
        return error;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
}
