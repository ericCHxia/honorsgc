/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: LoginRecordRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/8/31 下午10:42
 */

package com.hdu.honor.loginrecord;

import com.hdu.honor.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRecordRepository extends JpaRepository<LoginRecord,Integer> {
    public List<LoginRecord> getLoginRecordsByUserId(int id);
}
