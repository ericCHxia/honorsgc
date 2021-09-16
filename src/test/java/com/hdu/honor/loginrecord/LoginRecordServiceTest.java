/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: LoginRecordServiceTest.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/8/31 下午10:50
 */

package com.hdu.honor.loginrecord;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginRecordServiceTest {
    @Autowired
    private LoginRecordService service;

    @Test
    void getByUser() {
        System.out.println(service.getByUser(22));
    }
}