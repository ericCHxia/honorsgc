/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Actor.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午8:47
 */

package com.hdu.honor.community.attend;

import com.hdu.honor.user.User;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Table(name = "gttatd")
@Entity
public class Attend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "usrid")
    private User user;
    @Column(name = "recid")
    private Integer recordId;
}