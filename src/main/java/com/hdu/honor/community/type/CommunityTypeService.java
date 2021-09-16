/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityService.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/6 上午8:56
 */

package com.hdu.honor.community.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityTypeService {
    @Autowired
    private CommunityTypeRepository communityTypeRepository;
    public CommunityType get(int id){
        return communityTypeRepository.getCommunityTypeById(id);
    }
    public List<CommunityType> getAll(){
        return communityTypeRepository.findAll();
    }
}

