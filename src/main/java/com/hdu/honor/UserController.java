/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: UserController.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/11 上午11:55
 */

package com.hdu.honor;

import com.hdu.honor.content.Content;
import com.hdu.honor.content.ContentService;
import com.hdu.honor.user.User;
import com.hdu.honor.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    final private Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String userLogin(){
        return "login";
    }

    @GetMapping("/user")
    public String user(Authentication authentication,
                       Model model){
        User user = (User) authentication.getPrincipal();
        List<Content> contents = contentService.getByUser(user);
        model.addAttribute("user",user);
        model.addAttribute("contents",contents);
        return "user";
    }

    @GetMapping("/changepwd")
    public String changepwd(Model model){
        return "changepwd";
    }

    @PostMapping("/commitpwd")
    public String commitPwd(Authentication authentication,
                            HttpServletRequest request,
                            Model model,
                            @RequestHeader(value = "Referer",defaultValue = "/user") String referer){
        User user = (User) authentication.getPrincipal();
        String oldPassword = request.getParameter("pwd0");
        String newPassword = request.getParameter("npwd");
        model.addAttribute("url",referer);
        if (user.getPassword().equals(oldPassword)){
            user.setPwd(newPassword);
            userService.save(user);
            model.addAttribute("message","修改成功");
        }else{
            model.addAttribute("message","密码错误");
        }
        return "redirection";
    }
}
