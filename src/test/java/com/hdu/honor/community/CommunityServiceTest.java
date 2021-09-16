/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityServiceTest.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午8:34
 */

package com.hdu.honor.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CommunityServiceTest {
    @Autowired
    private CommunityService communityService;
    @Test
    void getByUserId() {
        System.out.println(communityService.getByUserId(20));
    }

    @Test
    void get() {
        Community community=communityService.get(7);
        Date date=community.getTim();
        date.getTime();
        System.out.println(communityService.get(7));
    }

    @Test
    void getParticipate() {
        System.out.println(communityService.getParticipate(7,0));
    }

    @Test
    void getByParticipant() {
        System.out.println(communityService.getByParticipant(30,0));
    }

    @Test
    void getIdsByParticipant() {
        List<Integer> types = new ArrayList<>();
        types.add(1);
        types.add(0);
        System.out.println(communityService.getIdsByParticipant(30,types));
    }
}