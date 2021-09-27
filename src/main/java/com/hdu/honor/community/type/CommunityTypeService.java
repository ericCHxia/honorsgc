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
    @Autowired
    private BaseCommunityTypeRepository baseCommunityTypeRepository;
    public CommunityType get(int id){
        return communityTypeRepository.getCommunityTypeById(id);
    }
    public List<CommunityType> getAll(){
        return communityTypeRepository.findAll();
    }
    public CommunityType save(CommunityType type) throws CommunityTypeNameExistException {
        if (type.getId()==null&&communityTypeRepository.getCommunityTypeByName(type.getName())!=null){
            throw new CommunityTypeNameExistException();
        }
        BaseCommunityType baseCommunityType = baseCommunityTypeRepository.saveAndFlush(type.toBaseCommunityType());
        return get(baseCommunityType.getId());
    }
    public void delete(CommunityType type){
        baseCommunityTypeRepository.delete(type.toBaseCommunityType());
    }
    public void delete(Integer id){
        baseCommunityTypeRepository.deleteById(id);
    }
}

