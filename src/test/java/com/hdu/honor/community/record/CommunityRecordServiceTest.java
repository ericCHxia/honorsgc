/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityRecordServiceTest.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/8 下午3:20
 */

package com.hdu.honor.community.record;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityRecordServiceTest {
    @Autowired
    private CommunityRecordService recordService;
    @Test
    void get() {
        System.out.println(recordService.get(15));
    }
}