/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: HonorAuthenticationSuccessHandler.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/1 上午11:57
 */

package com.hdu.honor.config;

import com.hdu.honor.loginrecord.LoginRecordService;
import com.hdu.honor.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主要实现了对登录成功后对登录信息的记录，将登录成功的信息写入到数据库中
 * @author Eric
 * @see WebSecurityConfig
 * @see LoginRecordService
 * @since 1.0
 */
@Service
public class HonorAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private LoginRecordService recordService;

    /**
     * 实现登录信息记录
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        recordService.add(user,"hdu",true,request);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
