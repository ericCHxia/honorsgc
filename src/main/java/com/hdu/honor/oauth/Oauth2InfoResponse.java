/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2InfoResponse.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午9:09
 */

package com.hdu.honor.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Oauth2InfoResponse {
    private boolean cache;
    private Oauth2InfoData data;
    private int error;
    private String msg;
    public void setCache(boolean cache) {
        this.cache = cache;
    }
    public boolean getCache() {
        return cache;
    }

    public void setData(Oauth2InfoData data) {
        this.data = data;
    }
    public Oauth2InfoData getData() {
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
