/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: BaseTag.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/26 下午4:52
 */

package com.hdu.honor.tag;

import com.hdu.honor.user.User;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tagtable")
@ToString
public class BaseTag {
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

    @Column(name = "tagname", length = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String tagName) {
        this.name = tagName;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "usrid")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
