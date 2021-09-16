/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityRecordService.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/8 下午3:18
 */

package com.hdu.honor.community.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommunityRecordService {
    @Autowired
    private CommunityRecordRepository recordRepository;
    public CommunityRecord get(int id){
        return recordRepository.getCommunityRecordById(id);
    }
    public CommunityRecord save(CommunityRecord record){return recordRepository.saveAndFlush(record);}
    public Integer delete(int id){
        return recordRepository.deleteCommunityRecordById(id);
    }
}
