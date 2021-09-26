/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2Controller.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午8:01
 */

package com.hdu.honor;

import com.hdu.honor.config.Oauth2Config;
import com.hdu.honor.oauth.Oauth2InfoResponse;
import com.hdu.honor.oauth.Oauth2Service;
import com.hdu.honor.oauth.Oauth2TokenResponse;
import com.hdu.honor.user.User;
import com.hdu.honor.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth2")
public class Oauth2Controller {
    @Autowired
    private Oauth2Config oauth2Config;
    @Autowired
    private Oauth2Service service;
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String oauth2login(@RequestParam(name = "code") String code,
                              @RequestParam(name = "state") String state,
                              Model model){
        try {
            Oauth2TokenResponse response = service.getToken(code);
            String num = response.getData().getStaffId();
            User user = userService.getByNum(num);
            if (user==null){
                Oauth2InfoResponse infoResponse = service.getInfo(response.getData().getAccessToken());
                user = infoResponse.getData().toUser();
                user = userService.save(user);
            }
            setLoginUser(user);
        }catch (Exception e){
            model.addAttribute("url","/login");
            model.addAttribute("message","授权失败："+e.getMessage());
            return "redirection";
        }
        model.addAttribute("url","/");
        model.addAttribute("message","登陆成功");
        return "redirection";
    }

    public static void setLoginUser(User userDetails) {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}
