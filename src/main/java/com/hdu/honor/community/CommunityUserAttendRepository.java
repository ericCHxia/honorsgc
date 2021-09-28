/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityUserAttendRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/27 下午11:51
 */

package com.hdu.honor.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommunityUserAttendRepository extends JpaRepository<CommunityUserAttend, CommunityUser>, JpaSpecificationExecutor<CommunityUserAttend> {
    List<CommunityUserAttend> findCommunityUserAttendsByCommunity(Community community);
}