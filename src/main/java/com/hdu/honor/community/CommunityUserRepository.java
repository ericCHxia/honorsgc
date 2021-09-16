/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityUserRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午11:31
 */

package com.hdu.honor.community;

import com.hdu.honor.user.User;
import com.hdu.honor.user.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityUserRepository extends UserRepository {
    @Query(value = "SELECT * FROM users WHERE users.id IN (SELECT usrid FROM gttptcp WHERE gttptcp.typ = :type AND gttptcp.gttid = :id);",nativeQuery = true)
    List<User> getParticipant(@Param("id") Integer id,@Param("type") Integer type);
}
