/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityRecord.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午9:15
 */

package com.hdu.honor.community.record;

import com.hdu.honor.community.attend.Attend;
import com.hdu.honor.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gttrec")
public class CommunityRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "usrid")
    private User user;
    @Column(name = "gttid")
    private Integer communityId;
    private String detail;
    private String img;
    private Date tim;
    @ManyToMany(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gttatd",joinColumns = @JoinColumn(name = "recid"),inverseJoinColumns = @JoinColumn(name = "usrid"))
    private List<User> users;
    public CommunityRecord(){
        this.tim = new Date();
    }
}
