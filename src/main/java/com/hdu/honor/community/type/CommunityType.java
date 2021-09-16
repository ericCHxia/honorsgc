/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Type.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 上午11:26
 */

package com.hdu.honor.community.type;

import lombok.Data;

import javax.persistence.*;

@Table(name = "gtttype_count")
@Entity
@Data
public class CommunityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String count;
}