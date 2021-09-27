/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: BaseCommunityType.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/27 下午5:17
 */

package com.hdu.honor.community.type;

import lombok.ToString;

import javax.persistence.*;

@Entity(name = "gtttype")
@ToString
public class BaseCommunityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, length = 100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
