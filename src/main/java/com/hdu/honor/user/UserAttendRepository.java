/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: UserAttendRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/11 下午5:10
 */

package com.hdu.honor.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserAttendRepository extends JpaRepository<UserAttend, Integer>, JpaSpecificationExecutor<UserAttend> {
}