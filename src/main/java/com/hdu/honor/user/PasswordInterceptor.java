/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: PasswordInterceptor.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午11:16
 */

package com.hdu.honor.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getUserPrincipal()==null){
            return true;
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user!=null&&user.getPassword().equals("670b14728ad9902aecba32e22fa4f6bd")){
            response.sendRedirect("/changepwd");
            return false;
        }
        return true;
    }
}
