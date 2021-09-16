/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: LoginRecordService.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/8/31 下午10:45
 */

package com.hdu.honor.loginrecord;

import com.hdu.honor.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class LoginRecordService {
    @Autowired
    private LoginRecordRepository repository;

    public List<LoginRecord> getByUser(User user){
        return getByUser(user.getId());
    }

    public List<LoginRecord> getByUser(int id){
        return repository.getLoginRecordsByUserId(id);
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public LoginRecord add(LoginRecord record){
        return repository.save(record);
    }

    public LoginRecord add(int userId, String method, boolean success, HttpServletRequest request){
        LoginRecord loginRecord=new LoginRecord(userId,method,getIpAddr(request),request.getHeader("user-agent"),success);
        return add(loginRecord);
    }

    public LoginRecord add(User user, String method, boolean success, HttpServletRequest request){
        return add(user.getId(),method,success,request);
    }
}
