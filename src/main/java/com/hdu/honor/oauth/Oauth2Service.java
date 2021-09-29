/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2Service.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午9:00
 */

package com.hdu.honor.oauth;

import com.hdu.honor.config.Oauth2Config;
import com.hdu.honor.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class Oauth2Service {
    private final Logger logger = LoggerFactory.getLogger(Oauth2Service.class);
    @Autowired
    private Oauth2Config oauth2Config;
    @Autowired
    private UserService userService;

    RestTemplate restTemplate = new RestTemplate();

    public Oauth2TokenResponse getToken(String code) throws Oauth2TokenException {
        logger.debug("TOKEN: "+code);
        Map<String,String> map = new HashMap<>();
        map.put("grant_type","authorization_code");
        logger.debug("TOKEN ID: "+oauth2Config.getId());
        map.put("client_id",oauth2Config.getId());
        map.put("client_secret",oauth2Config.getSecret());
        map.put("code",code);
        Oauth2TokenResponse response = restTemplate.getForObject("https://api.hduhelp.com/oauth/token?grant_type={grant_type}&client_id={client_id}&client_secret={client_secret}&code={code}",
                Oauth2TokenResponse.class,map);
        if (response==null){
            logger.debug("TOKEN: "+"response is None");
            throw new Oauth2TokenException("response is None");
        }
        if (response.getError()!=0){
            logger.debug("TOKEN: "+response.getMsg());
            throw new Oauth2TokenException(response.getMsg());
        }
        return response;
    }

    public Oauth2InfoResponse getInfo(String token) throws Oauth2InfoException{
        logger.debug("INFO: "+token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","token "+token);
        ResponseEntity<Oauth2InfoResponse> response = restTemplate.exchange("https://api.hduhelp.com/base/student/info", HttpMethod.GET,new HttpEntity<String>(headers),Oauth2InfoResponse.class);
        if (response.getStatusCode()!= HttpStatus.OK){
            logger.debug("INFO: "+ Objects.requireNonNull(response.getBody()).getMsg());
            throw new Oauth2InfoException(Objects.requireNonNull(response.getBody()).getMsg());
        }
        return response.getBody();
    }
}
