/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: BaseTagRepository.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/26 下午4:58
 */

package com.hdu.honor.tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseTagRepository extends JpaRepository<BaseTag, Integer> {
}