/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityTypeRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/6 上午8:50
 */

package com.hdu.honor.community.type;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityTypeRepository extends JpaRepository<CommunityType, Integer> {
    CommunityType getCommunityTypeById(int id);
}