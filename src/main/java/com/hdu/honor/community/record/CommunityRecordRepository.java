/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityRecordRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/8 下午3:05
 */

package com.hdu.honor.community.record;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRecordRepository extends JpaRepository<CommunityRecord, Integer> {
    List<CommunityRecord> getCommunityRecordsByCommunityId(int id);
    CommunityRecord getCommunityRecordById(int id);
    Integer deleteCommunityRecordById(int id);
}