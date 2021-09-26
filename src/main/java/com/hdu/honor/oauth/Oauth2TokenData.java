/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2Data.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午8:47
 */

package com.hdu.honor.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Oauth2TokenData {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token_expire")
    private int accessTokenExpire;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("refresh_token_expire")
    private int refreshTokenExpire;
    @JsonProperty("staff_id")
    private String staffId;
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessTokenExpire(int accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }
    public int getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshTokenExpire(int refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }
    public int getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public String getStaffId() {
        return staffId;
    }
}
