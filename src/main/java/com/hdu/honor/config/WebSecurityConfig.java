/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: WebSecurityConfig.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/1 上午11:39
 */

package com.hdu.honor.config;

import com.hdu.honor.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.sql.DataSource;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //设置用户信息服务
    @Autowired
    private  UserService userService;
    @Autowired
    private HonorAuthenticationFailureHandler failureHandler;
    @Autowired
    private HonorAuthenticationSuccessHandler successHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * 密码是以明文的方式比较和储存的，前端已经通过MD5将密码加密了
     */
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * <p>访问控制与登录设置</p>
     * <p>以下可以不需要登录就可以访问</p>
     * <ul>
     *     <li>”/login“</li>
     *     <li>"/css/**"</li>
     *     <li>"/js/**"</li>
     *     <li>"/img/**"</li>
     * </ul>
     * <p>全局登录设置</p>
     * <p>实现自定义登录页面，错误提示，登录成功页面自动转跳，登录信息记录等功能</p>
     * <p>还实现了基于Cookie可持久化登录，Cookie的有效时长为60*60*24*7秒（一周）</p>
     * <ul>
     *     <li>设置登录URL链接是/login</li>
     *     <li>登录成功后控制类为{@link HonorAuthenticationSuccessHandler}</li>
     *     <li>登录失败后控制类为{@link HonorAuthenticationFailureHandler}</li>
     * </ul>
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置浏览范围
        http.authorizeRequests().antMatchers("/login","/css/**","/js/**","/img/**").permitAll()
                .anyRequest().authenticated();
        //登录配置
        http.formLogin().loginPage("/login")
                .loginProcessingUrl("/verify")
                .passwordParameter("pwd")
                .usernameParameter("usn")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and()
                .rememberMe()
                .userDetailsService(userService)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60*24*7);
        http.logout().invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .deleteCookies("remember-me","JSESSIONID");
        http.csrf().ignoringAntMatchers("/upload");
//        http.cors().disable();
        http.headers().frameOptions().disable();
    }

    @Resource
    private DataSource dataSource;

    /**
     * 可持久化登录信息的储存仓库
     */
    @Bean
    protected PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true); // 启动创建表，创建成功后注释掉
        return tokenRepository;
    }
}
