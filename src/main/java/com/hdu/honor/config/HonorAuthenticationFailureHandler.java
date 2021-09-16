package com.hdu.honor.config;

import com.hdu.honor.loginrecord.LoginRecordService;
import com.hdu.honor.user.User;
import com.hdu.honor.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class HonorAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private LoginRecordService recordService;
    @Autowired
    private UserService userService;
    public HonorAuthenticationFailureHandler(){
        super("/login?error");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userName = request.getParameter("usn");
        logger.debug("Login failed: "+userName);
        try {
            User user = userService.loadUserByUsername(userName);
            recordService.add(user,"hdu",false,request);
        }catch (UsernameNotFoundException e){
            exception = new BadCredentialsException("学号不存在",exception);
            super.onAuthenticationFailure(request,response,exception);
            return;
        }
        if (exception instanceof LockedException) {
            exception = new LockedException("账户被锁定，请联系管理员!", exception);
        } else if (exception instanceof CredentialsExpiredException) {
            exception = new CredentialsExpiredException("密码过期，请联系管理员!", exception);
        } else if (exception instanceof AccountExpiredException) {
            exception = new AccountExpiredException("账户过期，请联系管理员!", exception);
        } else if (exception instanceof DisabledException) {
            exception = new DisabledException("账户被禁用，请联系管理员!", exception);
        } else if (exception instanceof BadCredentialsException) {
            exception = new BadCredentialsException("用户名或者密码输入错误，请重新输入!", exception);
        }
        super.onAuthenticationFailure(request,response,exception);
    }
}
